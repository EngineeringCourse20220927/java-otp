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
}
