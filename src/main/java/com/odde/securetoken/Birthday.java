package com.odde.securetoken;

import java.time.LocalDate;

import static java.time.Month.APRIL;

public class Birthday {

    public boolean isBirthday() {
        LocalDate today = LocalDate.now();
        return today.getDayOfMonth() == 9 && today.getMonth().equals(APRIL);
    }
}