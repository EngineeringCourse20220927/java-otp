package com.odde.securetoken;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

public class Budget {
    private int year;
    private Month month;
    private int amount;

    public Budget(int year, Month month, int amount) {
        this.year = year;
        this.month = month;
        this.amount = amount;
    }

    public int getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public int getAmount() {
        return amount;
    }

    public YearMonth getYearMonth() {
        return YearMonth.of(year, month);
    }

    public int getDailyAmount() {
        return amount / getYearMonth().lengthOfMonth();
    }

    public LocalDate getEndDate() {
        return getYearMonth().atEndOfMonth();
    }

    public LocalDate getStartDate() {
        return getYearMonth().atDay(1);
    }

    public Period getPeriod() {
        return new Period(getStartDate(), getEndDate());
    }

    public int getOverlappingAmount(Period period) {
        return getDailyAmount() * period.getOverlappingPeriod(getPeriod()).getDayCount();
    }
}
