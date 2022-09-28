package com.odde.securetoken;

import java.time.LocalDate;

public class Period {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Period(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getDayCount() {
        if (startDate.isAfter(endDate)) {
            return 0;
        }
        return java.time.Period.between(startDate, endDate).getDays() + 1;
    }

    public Period getOverlappingPeriod(Period budgetPeriod) {
        LocalDate overlappingStartDate = budgetPeriod.startDate.isAfter(startDate) ? budgetPeriod.startDate : startDate;
        LocalDate overlappingEndDate = budgetPeriod.endDate.isBefore(endDate) ? budgetPeriod.endDate : endDate;
        return new Period(overlappingStartDate, overlappingEndDate);
    }
}
