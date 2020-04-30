package com.example.mydinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean isValidUsername(String username) {
        final String USERNAME_PATTERN = "^[a-zA-Z]{3,20}$";

        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        final String PASSWORD_PATTERN = "^[a-zA-Z0-9.!@_]{5,20}$";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        final String EMAIL_PATTERN = "^([a-zA-Z0-9])+[\\w.-]*@(([\\w-])+\\.)+([a-zA-Z]){2,}$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches() && email.length() >= 10 && email.length() <= 50;
    }
}
