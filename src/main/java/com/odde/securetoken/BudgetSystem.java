package com.odde.securetoken;

import java.time.LocalDate;
import java.time.Month;

public class BudgetSystem {

    private BudgetService service;

    public BudgetSystem(BudgetService service) {
        this.service = service;
    }

    public int query(LocalDate startDate, LocalDate endDate) {
        if (!validate(startDate, endDate)) {
            return 0;
        }
        return calculatorBudget(startDate, endDate);
    }

    private int calculatorBudget(LocalDate startDate, LocalDate endDate) {
        if (startDate.getYear() == endDate.getYear() && startDate.getMonth() == endDate.getMonth()) {
            int days = endDate.getDayOfMonth() - startDate.getDayOfMonth() + 1;
            return getBudgetByMonth(startDate.getYear(), startDate.getMonth()) / startDate.lengthOfMonth() * days;
        }
        int total = 0;
        total += getBudgetFromCurrentToMonthEnd(startDate);
        LocalDate currentDate = startDate.plusMonths(1);
        while (LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1).isBefore(LocalDate.of(endDate.getYear(), endDate.getMonth(), 1))) {
            total += getBudgetByMonth(currentDate.getYear(), currentDate.getMonth());
            currentDate = currentDate.plusMonths(1);
        }

        total += getBudgetFromMonthStartToCurrent(endDate);
        return total;
    }

    private int getBudgetByMonth(int year, Month month) {
        return service.queryAll().stream()
                .filter(budget -> budget.getYear() == year && budget.getMonth() == month)
                .findFirst().map(Budget::getAmount).orElse(0);
    }

    private int getBudgetFromCurrentToMonthEnd(LocalDate endDate) {
        int currentMonthBudget = getBudgetByMonth(endDate.getYear(), endDate.getMonth());
        return (currentMonthBudget / endDate.lengthOfMonth()) * (endDate.lengthOfMonth() - endDate.getDayOfMonth() + 1);
    }

    private int getBudgetFromMonthStartToCurrent(LocalDate startDate) {
        int currentMonthBudget = getBudgetByMonth(startDate.getYear(), startDate.getMonth());
        return (currentMonthBudget / startDate.lengthOfMonth()) * startDate.getDayOfMonth();
    }

    private boolean validate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            return false;
        }
        return true;
    }
}
