package com.example.mydinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);

        Button loginBtn = findViewById(R.id.login_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input_username = username.getText().toString();
                String input_password = password.getText().toString();

                //clean errors
                username.setError(null);
                password.setError(null);

                if (Validation.isValidUsername(input_username) && Validation.isValidPassword(input_password)) {
                    Toast.makeText(LoginActivity.this, "Sveiki, " + username.getText().toString() + "!",
                            Toast.LENGTH_LONG).show();

                    Intent goToSearchActivity = new Intent(LoginActivity.this, SearchActivity.class);
                    startActivity(goToSearchActivity);
                } else {
                    username.setError(getResources().getString(R.string.login_invalid_credentials));
                    username.requestFocus();
                }

            }
        });


        Button registerBtn = findViewById(R.id.register_button);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goToRegisterActivity);
            }
        });

    }


}
