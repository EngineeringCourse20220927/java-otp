package com.odde.securetoken;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewBudgetTest {

    BudgetRepo stubBudgetRepo = mock(BudgetRepo.class);
    NewBudgetService budgetService = new NewBudgetService(stubBudgetRepo);

    @Test
    public void no_budget() {
        givenBudgets();

        long amount = budgetService.query(LocalDate.of(2022, 9, 28), LocalDate.of(2022, 9, 29));

        assertEquals(0, amount);
    }

    @Test
    public void start_and_end_is_same() {
        givenBudgets(newBudget(2022, 9, 30));

        long amount = budgetService.query(LocalDate.of(2022, 9, 28), LocalDate.of(2022, 9, 28));

        assertEquals(1, amount);
    }

    @Test
    public void start_and_end_one_day_between() {
        givenBudgets(newBudget(2022, 9, 30));

        long amount = budgetService.query(LocalDate.of(2022, 9, 28), LocalDate.of(2022, 9, 29));

        assertEquals(2, amount);
    }

    @Test
    public void start_is_before_budget_start() {
        givenBudgets(newBudget(2022, 9, 30));

        long amount = budgetService.query(LocalDate.of(2022, 8, 28), LocalDate.of(2022, 9, 5));

        assertEquals(5, amount);
    }

    @Test
    public void end_is_after_budget_end() {
        givenBudgets(newBudget(2022, 9, 30));

        long amount = budgetService.query(LocalDate.of(2022, 9, 28), LocalDate.of(2022, 10, 5));

        assertEquals(3, amount);
    }

    @Test
    public void start_is_after_budget_end() {
        givenBudgets(newBudget(2022, 9, 30));

        long amount = budgetService.query(LocalDate.of(2022, 10, 3), LocalDate.of(2022, 10, 5));

        assertEquals(0, amount);
    }

    @Test
    public void end_is_before_budget_start() {
        givenBudgets(newBudget(2022, 9, 30));

        long amount = budgetService.query(LocalDate.of(2022, 8, 25), LocalDate.of(2022, 8, 27));

        assertEquals(0, amount);
    }

    @Test
    public void two_budgets() {
        givenBudgets(newBudget(2022, 9, 30), newBudget(2022, 10, 31));

        long amount = budgetService.query(LocalDate.of(2022, 9, 30), LocalDate.of(2022, 10, 1));

        assertEquals(2, amount);
    }

    @Test
    public void amount_is_not_1() {
        givenBudgets(newBudget(2022, 9, 60), newBudget(2022, 10, 93));

        long amount = budgetService.query(LocalDate.of(2022, 9, 30), LocalDate.of(2022, 10, 1));

        assertEquals(2 + 3, amount);
    }

    private void givenBudgets(NewBudget... budgets) {
        when(stubBudgetRepo.findAll()).thenReturn(asList(budgets));
    }

    private NewBudget newBudget(int year, int month, int amount) {
        return new NewBudget(YearMonth.of(year, month), amount);
    }

}
