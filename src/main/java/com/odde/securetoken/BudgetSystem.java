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
        int total = 0;
        LocalDate currentDate = period.getStartDate();
        while (currentDate.withDayOfMonth(1).isBefore(period.getEndDate())) {
            Budget budget = getBudget(YearMonth.from(currentDate));
            total += budget.getOverlappingAmount(period);
            currentDate = currentDate.plusMonths(1);
        }
        return total;
    }

    private Budget getBudget(YearMonth yearMonth) {
        return service.queryAll().stream()
                .filter(budget -> budget.getYearMonth().equals(yearMonth))
                .findFirst().orElse(new Budget(yearMonth.getYear(), yearMonth.getMonth(), 0));
    }

}
