package com.example.mydinner;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    private String usernameRegister, password, email;
    private static final String PREFERENCES_FILE_NAME = "com.example.mydinner";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String  REMEMBER_ME_KEY = "remember_me";
    private SharedPreferences sharedPreferences;

    // Use for new user registration
    public User(String usernameRegister, String password, String email) {
        this.usernameRegister = usernameRegister;
        this.password = password;
        this.email = email;
    }

    // Use to login a user(remembered)
    public User(Context context) {
        this.sharedPreferences = context.getSharedPreferences(User.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public boolean isRemembered() {
        return this.sharedPreferences.getBoolean(REMEMBER_ME_KEY, false);
    }

    public void setRemembered(boolean remembered) {
        this.sharedPreferences.edit().putBoolean(REMEMBER_ME_KEY, remembered).apply();
    }

    public String getUsernameForLogin() {
        return this.sharedPreferences.getString(USERNAME_KEY, "");
    }

    public void setUsernameForLogin(String username) {
        this.sharedPreferences.edit().putString(USERNAME_KEY, username).apply();
    }

    public String getPasswordForLogin() {
        return this.sharedPreferences.getString(PASSWORD_KEY, "");
    }

    public void setPasswordForLogin(String password) {
        this.sharedPreferences.edit().putString(PASSWORD_KEY, password).apply();
    }


    // for registration
    public String getUsernameForRegistration() {
        return this.usernameRegister;
    }

    public void setUsernameForRegistration(String username) {
        this.usernameRegister = username;
    }

    public String getPasswordForRegistration() {
        return this.password;
    }

    public void setPasswordForRegistration(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
