package com.odde.securetoken;

import java.time.LocalDate;

import static java.time.Month.APRIL;

public class Birthday {

    private TimeProvider timeProvider;

    public Birthday(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public boolean isBirthday() {
        LocalDate today = timeProvider.today();
        return today.getDayOfMonth() == 9 && today.getMonth().equals(APRIL);
    }

    public static class TimeProvider {

        public LocalDate today() {
            return LocalDate.now();
        }
    }
}