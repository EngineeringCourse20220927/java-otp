package com.odde.securetoken;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class NewPeriod {
    private final LocalDate start;
    private final LocalDate end;

    public NewPeriod(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public long getOverlappingDayCount(NewPeriod budgetNewPeriod) {
        LocalDate overlappingStart = getStart().isBefore(budgetNewPeriod.getStart()) ? budgetNewPeriod.getStart() : getStart();
        LocalDate overlappingEnd = budgetNewPeriod.getEnd().isBefore(getEnd()) ? budgetNewPeriod.getEnd() : getEnd();
        if (overlappingStart.isAfter(overlappingEnd)) {
            return 0;
        }
        return DAYS.between(overlappingStart, overlappingEnd) + 1;
    }
}
