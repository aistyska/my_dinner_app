package com.example.mydinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

                    String hashedPass = new String(Hex.encodeHex(DigestUtils.sha256(input_password)));
                    User user = new User(input_username, hashedPass, input_email);

                    insertUserToDB(user);

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


    public void insertUserToDB(final User user){

        class NewUser extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                String insertUserUrl = "http://aistyapp.byethost31.com/insertUser.php";

                List<NameValuePair> nameValue = new ArrayList<NameValuePair>();

                nameValue.add(new BasicNameValuePair("username", user.getUsernameForRegistration()));
                nameValue.add(new BasicNameValuePair("password", user.getPasswordForRegistration()));
                nameValue.add(new BasicNameValuePair("email", user.getEmail()));

                try{
                    BasicCookieStore cookieStore = new BasicCookieStore();
                    BasicClientCookie cookie = new BasicClientCookie("__test", "e5e4b30a3699e26c5ecd32704080416c");
                    cookie.setDomain(".aistyapp.byethost31.com");
                    cookie.setPath("/");
                    cookieStore.addCookie(cookie);

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(insertUserUrl);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
                    HttpContext localContext = new BasicHttpContext();
                    localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
                    HttpResponse httpResponse = httpClient.execute(httpPost, localContext);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    StringBuffer result = new StringBuffer();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                    return e.toString();
                }

            }

            @Override
            protected void onPostExecute(String result) {

                if (result.equals("user saved")) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();

                    Intent goToLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(goToLoginActivity);
                } else if (result.equals("user already exists")) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.register_user_exists), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, getString(R.string.register_failed), Toast.LENGTH_LONG).show();
                }

            }
        }

        NewUser newUser = new NewUser();
        newUser.execute();
    }



}
