package com.example.mydinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText username = findViewById(R.id.username);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);

        Button registerBtn = findViewById(R.id.register_button);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input_username = username.getText().toString();
                String input_email = email.getText().toString();
                String input_password = password.getText().toString();

                username.setError(null);
                email.setError(null);
                password.setError(null);

                if (Validation.isValidUsername(input_username) &&
                        Validation.isValidEmail(input_email) &&
                        Validation.isValidPassword(input_password)) {

                    Toast.makeText(RegisterActivity.this,
                        getResources().getString(R.string.login_username) + ": " + input_username + "\n" +
                                getResources().getString(R.string.register_email) + ": " + input_email + "\n" +
                                getResources().getString(R.string.login_password) + ": " + input_password,
                            Toast.LENGTH_LONG).show();

                    Intent goToLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(goToLoginActivity);

                } else {
                    if (!Validation.isValidUsername(input_username)){
                        username.setError(getResources().getString(R.string.register_invalid_username));
                    }
                    if (!Validation.isValidEmail(input_email)){
                        email.setError(getResources().getString(R.string.register_ivalid_email));
                    }
                    if (!Validation.isValidPassword(input_password)){
                        password.setError(getResources().getString(R.string.register_invalid_password));
                    }
                }

            }
        });
    }
}
