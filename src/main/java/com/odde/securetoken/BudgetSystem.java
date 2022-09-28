package com.odde.securetoken;

import java.time.LocalDate;
import java.time.Period;
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
        return calculateBudget(startDate, endDate);
    }

    private int calculateBudget(LocalDate startDate, LocalDate endDate) {
        if (YearMonth.from(startDate).equals(YearMonth.from(endDate))) {
            int days = Period.between(startDate, endDate).getDays() + 1;
            return getBudget(YearMonth.from(startDate)).getDailyAmount() * days;
        }
        int total = 0;
        total += getBudgetFromCurrentToMonthEnd(startDate);
        LocalDate currentDate = startDate.plusMonths(1);
        while (currentDate.withDayOfMonth(1).isBefore(endDate.withDayOfMonth(1))) {
            Budget budget = getBudget(YearMonth.from(currentDate));
            total += budget.getDailyAmount() * (Period.between(budget.getStartDate(), budget.getEndDate()).getDays() + 1);
            currentDate = currentDate.plusMonths(1);
        }

        total += getBudgetFromMonthStartToCurrent(endDate);
        return total;
    }

    private Budget getBudget(YearMonth yearMonth) {
        return service.queryAll().stream()
                .filter(budget -> budget.getYearMonth().equals(yearMonth))
                .findFirst().orElse(new Budget(yearMonth.getYear(), yearMonth.getMonth(), 0));
    }

    private int getBudgetFromCurrentToMonthEnd(LocalDate startDate) {
        Budget budget = getBudget(YearMonth.from(startDate));
        return budget.getDailyAmount() * (Period.between(startDate, budget.getEndDate()).getDays() + 1);
    }

    private int getBudgetFromMonthStartToCurrent(LocalDate endDate) {
        Budget budget = getBudget(YearMonth.from(endDate));
        return budget.getDailyAmount() * (Period.between(budget.getStartDate(), endDate).getDays() + 1);
    }

}
