package com.odde.securetoken;

import com.odde.securetoken.AuthenticationService.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    ProfileDao stubProfileDao = mock(ProfileDao.class);
    RsaTokenDao stubRsaTokenDao = mock(RsaTokenDao.class);
    Logger mockLogger = mock(Logger.class);
    AuthenticationService target = new AuthenticationService(stubProfileDao, stubRsaTokenDao, mockLogger);

    @Test
    public void is_valid_test() {
        givenPassword("91");
        givenToken("000000");

        boolean actual = target.isValid("joey", "91"+ "000000");

        assertTrue(actual);
    }

    @Test
    public void is_not_valid_test() {
        givenPassword("91");
        givenToken("wrong");

        boolean actual = target.isValid("joey", "91000000");

        assertFalse(actual);
    }

    @Test
    public void print_login_failed() {
        givenPassword("91");
        givenToken("000000");

        target.isValid("joey", "91000001");

        verify(mockLogger).message(eq("login failed"));
    }

    public static class MockLogger implements Logger {
        public String getMsg() {
            return msg;
        }

        private String msg;

        @Override
        public void message(String msg) {
            this.msg = msg;
        }
    }

    private void givenToken(String token) {
        when(stubRsaTokenDao.getRandom(anyString())).thenReturn(token);
    }

    private void givenPassword(String password) {
        when(stubProfileDao.getPassword(anyString())).thenReturn(password);
    }

}
