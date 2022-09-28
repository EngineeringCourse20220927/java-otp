package com.odde.securetoken;

import java.time.LocalDate;
import java.time.YearMonth;

public class BudgetSystem {

    private BudgetService service;

    public BudgetSystem(BudgetService service) {
        this.service = service;
    }

    public int query(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            return 0;
        }
        return calculateBudget(new Period(startDate, endDate));
    }

    private int calculateBudget(Period period) {
        if (YearMonth.from(period.getStartDate()).equals(YearMonth.from(period.getEndDate()))) {
            return getBudget(YearMonth.from(period.getStartDate())).getDailyAmount() * period.getDayCount();
        }
        int total = 0;
        total += getBudgetFromCurrentToMonthEnd(period.getStartDate());
        LocalDate currentDate = period.getStartDate().plusMonths(1);
        while (currentDate.withDayOfMonth(1).isBefore(period.getEndDate().withDayOfMonth(1))) {
            Budget budget = getBudget(YearMonth.from(currentDate));
            total += budget.getDailyAmount() * new Period(budget.getStartDate(), budget.getEndDate()).getDayCount();
            currentDate = currentDate.plusMonths(1);
        }

        total += getBudgetFromMonthStartToCurrent(period.getEndDate());
        return total;
    }

    private Budget getBudget(YearMonth yearMonth) {
        return service.queryAll().stream()
                .filter(budget -> budget.getYearMonth().equals(yearMonth))
                .findFirst().orElse(new Budget(yearMonth.getYear(), yearMonth.getMonth(), 0));
    }

    private int getBudgetFromCurrentToMonthEnd(LocalDate startDate) {
        Budget budget = getBudget(YearMonth.from(startDate));
        return budget.getDailyAmount() * new Period(startDate, budget.getEndDate()).getDayCount();
    }

    private int getBudgetFromMonthStartToCurrent(LocalDate endDate) {
        Budget budget = getBudget(YearMonth.from(endDate));
        return budget.getDailyAmount() * new Period(budget.getStartDate(), endDate).getDayCount();
    }

}
