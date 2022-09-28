package com.odde.securetoken;

import java.time.LocalDate;

public class Period {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Period(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getDayCount() {
        return java.time.Period.between(startDate, endDate).getDays() + 1;
    }

    public Period getOverlappingPeriod(Period budgetPeriod) {
        LocalDate overlappingStartDate = budgetPeriod.getStartDate().isAfter(getStartDate()) ? budgetPeriod.getStartDate() : getStartDate();
        LocalDate overlappingEndDate = budgetPeriod.getEndDate().isBefore(getEndDate()) ? budgetPeriod.getEndDate() : getEndDate();
        return new Period(overlappingStartDate, overlappingEndDate);
    }
}
