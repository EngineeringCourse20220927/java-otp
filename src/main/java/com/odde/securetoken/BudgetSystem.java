package com.odde.securetoken;

import java.time.LocalDate;

public class BudgetSystem {

    private final BudgetService service;

    public BudgetSystem(BudgetService service) {
        this.service = service;
    }

    public int query(LocalDate startDate, LocalDate endDate) {
        return service.queryAll().stream()
                .mapToInt(budget -> budget.getOverlappingAmount(new Period(startDate, endDate)))
                .sum();
    }

}
