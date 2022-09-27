package com.odde.securetoken;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestBudget {

    @Test
    public void testEndTimeBeforeStartTime() {
        BudgetSystem budgetSystem = new BudgetSystem(getBudgetService());
        LocalDate startDate = LocalDate.of(2022, Month.SEPTEMBER, 27);
        LocalDate endDate = LocalDate.of(2022, Month.SEPTEMBER, 26);

        int budget = budgetSystem.query(startDate, endDate);

        assertThat(budget).isEqualTo(0);
    }

    @Test
    public void testWholeMonth() {
        BudgetSystem budgetSystem = new BudgetSystem(getBudgetService());
        LocalDate startDate = LocalDate.of(2022, Month.AUGUST, 1);
        LocalDate endDate = LocalDate.of(2022, Month.AUGUST, 31);

        int budget = budgetSystem.query(startDate, endDate);
        assertEquals(budget, 31);
    }

    @Test
    public void test815to915() {
        BudgetSystem budgetSystem = new BudgetSystem(getBudgetService());
        LocalDate startDate = LocalDate.of(2022, Month.AUGUST, 15);
        LocalDate endDate = LocalDate.of(2022, Month.SEPTEMBER, 15);

        int budget = budgetSystem.query(startDate, endDate);
        assertEquals(budget, 32);
    }

    @Test
    public void test56to723() {
        BudgetSystem budgetSystem = new BudgetSystem(getBudgetService());
        LocalDate startDate = LocalDate.of(2022, Month.MAY, 6);
        LocalDate endDate = LocalDate.of(2022, Month.JULY, 23);
        int budget = budgetSystem.query(startDate, endDate);
        assertEquals(budget, 23);
    }

    @Test
    public void test20210801to20220310() {
        BudgetSystem budgetSystem = new BudgetSystem(getBudgetService());
        LocalDate startDate = LocalDate.of(2021, Month.AUGUST, 1);
        LocalDate endDate = LocalDate.of(2022, Month.MARCH, 10);
        int budget = budgetSystem.query(startDate, endDate);
        assertEquals(budget, 222);
    }

    @Test
    public void test20191210to20200405() {
        BudgetSystem budgetSystem = new BudgetSystem(getBudgetService());
        LocalDate startDate = LocalDate.of(2019, Month.DECEMBER, 10);
        LocalDate endDate = LocalDate.of(2020, Month.APRIL, 5);
        int budget = budgetSystem.query(startDate, endDate);
        assertEquals(budget, 118);
    }

    @Test
    public void begin_month_has_no_budget() {
        BudgetSystem budgetSystem = new BudgetSystem(getBudgetService());
        LocalDate startDate = LocalDate.of(2018, Month.DECEMBER, 10);
        LocalDate endDate = LocalDate.of(2019, Month.JANUARY, 10);
        int budget = budgetSystem.query(startDate, endDate);
        assertEquals(budget, 10);
    }

    @Test
    public void end_month_has_no_budget() {
        BudgetSystem budgetSystem = new BudgetSystem(getBudgetService());
        LocalDate startDate = LocalDate.of(2022, Month.DECEMBER, 10);
        LocalDate endDate = LocalDate.of(2023, Month.JANUARY, 11);
        int budget = budgetSystem.query(startDate, endDate);
        assertEquals(budget, 22);
    }

    @Test
    public void middle_month_no_budget() {
        BudgetService stubBudgetService = mock(BudgetService.class);
        when(stubBudgetService.queryAll()).thenReturn(asList(new Budget(2022, Month.JANUARY, 62), new Budget(2022, Month.MARCH, 93)));
        BudgetSystem budgetSystem = new BudgetSystem(stubBudgetService);
        LocalDate startDate = LocalDate.of(2022, Month.JANUARY, 15);
        LocalDate endDate = LocalDate.of(2022, Month.MARCH, 8);
        int budget = budgetSystem.query(startDate, endDate);
        assertEquals(budget, 17 * 2 + 8 * 3);
    }


    private BudgetService getBudgetService() {
        return new BudgetService() {
            @Override
            public List<Budget> queryAll() {
                List<Budget> budgetList = new ArrayList<>();
                //
                LocalDate current = LocalDate.of(2019, Month.JANUARY, 1);
                LocalDate end = LocalDate.of(2022, Month.DECEMBER, 31);
                while (current.isBefore(end)) {
                    if (Month.JUNE == current.getMonth()) {
                        current = current.plusDays(1);
                        continue;
                    }
                    budgetList.add(new Budget(current.getYear(), current.getMonth(), current.getMonth() == Month.MAY ? 0 : current.lengthOfMonth()));
                    current = current.plusDays(1);
                }
                return budgetList;
            }
        };
    }
}
