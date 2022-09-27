package com.odde.securetoken;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BirthdayTest {

    @Test
    public void is_not_birthday() {
        Birthday birthday = new Birthday(new Birthday.TimeProvider());
        assertThat(birthday.isBirthday()).isFalse();
    }

    @Test
    public void is_birthday() {
        Birthday birthday = new Birthday(new Birthday.TimeProvider() {
            @Override
            public LocalDate today() {
                return LocalDate.of(2022, 4,9);
            }
        });
        assertThat(birthday.isBirthday()).isTrue();
    }
}