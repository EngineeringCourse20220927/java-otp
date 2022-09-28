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
        return calculateBudget(startDate, endDate);
    }

    private int calculateBudget(LocalDate startDate, LocalDate endDate) {
        if (YearMonth.from(startDate).equals(YearMonth.from(endDate))) {
            int days = endDate.getDayOfMonth() - startDate.getDayOfMonth() + 1;
            return getBudgetByMonth(YearMonth.from(startDate)) / startDate.lengthOfMonth() * days;
        }
        int total = 0;
        total += getBudgetFromCurrentToMonthEnd(startDate);
        LocalDate currentDate = startDate.plusMonths(1);
        while (LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1).isBefore(LocalDate.of(endDate.getYear(), endDate.getMonth(), 1))) {
            total += getBudgetByMonth(YearMonth.from(currentDate));
            currentDate = currentDate.plusMonths(1);
        }

        total += getBudgetFromMonthStartToCurrent(endDate);
        return total;
    }

    private int getBudgetByMonth(YearMonth yearMonth) {
        return service.queryAll().stream()
                .filter(budget -> budget.getYearMonth().equals(yearMonth))
                .findFirst().map(Budget::getAmount).orElse(0);
    }

    private int getBudgetFromCurrentToMonthEnd(LocalDate endDate) {
        int currentMonthBudget = getBudgetByMonth(YearMonth.from(endDate));
        return (currentMonthBudget / endDate.lengthOfMonth()) * (endDate.lengthOfMonth() - endDate.getDayOfMonth() + 1);
    }

    private int getBudgetFromMonthStartToCurrent(LocalDate startDate) {
        int currentMonthBudget = getBudgetByMonth(YearMonth.from(startDate));
        return (currentMonthBudget / startDate.lengthOfMonth()) * startDate.getDayOfMonth();
    }

}
