package com.odde.securetoken;

import java.time.LocalDate;
import java.time.YearMonth;

public class NewBudget {
    private final YearMonth yearMonth;
    private final int amount;

    public NewBudget(YearMonth yearMonth, int amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public LocalDate getStart() {
        return yearMonth.atDay(1);
    }

    public LocalDate getEnd() {
        return yearMonth.atEndOfMonth();
    }

    public NewPeriod getPeriod() {
        return new NewPeriod(getStart(), getEnd());
    }

    public int getAmount() {
        return amount;
    }

    public long getOverlappingAmount(NewPeriod period) {
        return getAmount() / getYearMonth().lengthOfMonth() * period.getOverlappingDayCount(getPeriod());
    }
}
