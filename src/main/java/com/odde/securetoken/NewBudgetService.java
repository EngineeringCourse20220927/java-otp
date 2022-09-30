package com.odde.securetoken;

import java.time.LocalDate;

public class NewBudgetService {
    private final BudgetRepo budgetRepo;

    public NewBudgetService(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public long query(LocalDate start, LocalDate end) {
        return budgetRepo.findAll().stream()
                .mapToLong(budget -> budget.getOverlappingAmount(new NewPeriod(start, end)))
                .sum();
    }

}
