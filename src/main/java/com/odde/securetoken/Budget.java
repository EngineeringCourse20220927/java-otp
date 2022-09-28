package com.odde.securetoken;

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

    public int getOverlappingAmount(Period period) {
        return amount / getYearMonth().lengthOfMonth() * period.getOverlappingPeriod(
                new Period(getYearMonth().atDay(1), getYearMonth().atEndOfMonth())).getDayCount();
    }

    private YearMonth getYearMonth() {
        return YearMonth.of(year, month);
    }
}
