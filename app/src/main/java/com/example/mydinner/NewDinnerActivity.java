package com.example.mydinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NewDinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dinner);

        final CheckBox checkedSoup = findViewById(R.id.check_soup);
        final CheckBox checkMainDish = findViewById(R.id.check_main_dish);
        final CheckBox checkSalad = findViewById(R.id.check_salad);

        final EditText dinnerName = findViewById(R.id.dinner_name);
        final EditText dinnerPrice = findViewById(R.id.dinner_price);

        final RadioGroup radioDeliveryGroup = findViewById(R.id.radio_delivery);

        final Spinner spinnerPaymentType = findViewById(R.id.payment_type);

        Button addNewDinnerBtn =  findViewById(R.id.add_dinner_button);

        addNewDinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean soup = checkedSoup.isChecked();
                boolean mainDish = checkMainDish.isChecked();
                boolean salad = checkSalad.isChecked();

                String inputDinnerName = dinnerName.getText().toString();
                String inputDinnerPrice = dinnerPrice.getText().toString();

                // finds radio button id which is selected
                int selectedDeliveryId = radioDeliveryGroup.getCheckedRadioButtonId();
                // finds the radio button by returned id
                RadioButton selectedRadioDelivery = findViewById(selectedDeliveryId);
                // gets value from selected radio button
                String radioDeliveryValue = selectedRadioDelivery.getText().toString();
                boolean deliverable = false;
                if (radioDeliveryValue == "Taip") {
                    deliverable = true;
                }

                //gets value from spinner
                String paymentType = String.valueOf(spinnerPaymentType.getSelectedItem());

                checkedSoup.setError(null);
                dinnerName.setError(null);
                dinnerPrice.setError(null);

                if (Validation.isValidDinnerName(inputDinnerName) &&
                        Validation.isValidDinnerPrice(inputDinnerPrice) &&
                        (soup || mainDish || salad)) {

                    String dinnerType = "";
                    if(soup) {
                        dinnerType += checkedSoup.getText().toString() + " ";
                    }
                    if (mainDish) {
                        dinnerType += checkMainDish.getText().toString() + " ";
                    }
                    if (salad) {
                        dinnerType += checkSalad.getText().toString() + " ";
                    }

                    // converts input string value to double
                    double Price = Double.parseDouble(inputDinnerPrice);

                    Dinner dinner = new Dinner(dinnerType, inputDinnerName, Price, deliverable, paymentType);

                    Toast.makeText(NewDinnerActivity.this,
                            getResources().getString(R.string.add_dinner_type) + ": " +  dinner.getDishType() + "\n" +
                                    getResources().getString(R.string.add_dinner_name) + ": " + dinner.getDishName() + "\n" +
                                    getResources().getString(R.string.add_dinner_price) + ": " + dinner.getPrice() + "\n" +
                                    getResources().getString(R.string.add_dinner_delivery) + ": " + radioDeliveryValue + "\n" +
                                    getResources().getString(R.string.add_dinner_payment) + ": " + dinner.getPaymentType(),
                            Toast.LENGTH_SHORT).show();
                    insertDinnerToDB(dinner);



                } else {
                    if (!Validation.isValidDinnerName(inputDinnerName)) {
                        dinnerName.setError(getResources().getString(R.string.dish_name_error));
                    }
                    if (!Validation.isValidDinnerPrice(inputDinnerPrice)) {
                        dinnerPrice.setError(getResources().getString(R.string.dinner_price_error));
                    }
                    if (!soup && !mainDish && !salad) {
                        checkedSoup.setFocusableInTouchMode(true);
                        checkedSoup.setError(getResources().getString(R.string.dish_type_error));
                    }
                }


            }
        });
    }


    public void insertDinnerToDB(final Dinner dinner) {

        class InsertDinner extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String serverURL = "http://aistyapp.byethost31.com/insertDinner.php";

                List<NameValuePair> nameValue = new ArrayList<NameValuePair>();

                nameValue.add(new BasicNameValuePair("type", dinner.getDishType()));
                nameValue.add(new BasicNameValuePair("name", dinner.getDishName()));
                nameValue.add(new BasicNameValuePair("price", String.valueOf(dinner.getPrice())));
                nameValue.add(new BasicNameValuePair("deliverable", String.valueOf(dinner.isDeliverable())));
                nameValue.add(new BasicNameValuePair("payment", dinner.getPaymentType()));

                try {
                    BasicCookieStore cookieStore = new BasicCookieStore();
                    BasicClientCookie cookie = new BasicClientCookie("__test", "e5e4b30a3699e26c5ecd32704080416c");
                    cookie.setDomain(".aistyapp.byethost31.com");
                    cookie.setPath("/");
                    cookieStore.addCookie(cookie);

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(serverURL);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
                    HttpContext localContext = new BasicHttpContext();
                    localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
                    HttpResponse httpResponse = httpClient.execute(httpPost, localContext);
                    return String.valueOf(httpResponse.getStatusLine().getStatusCode());

                } catch (ClientProtocolException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                    return e.toString();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s.equals(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                    Toast.makeText(NewDinnerActivity.this, getString(R.string.dinner_saved_success), Toast.LENGTH_LONG).show();

                    Intent goToSearchActivity = new Intent(NewDinnerActivity.this, SearchActivity.class);
                    startActivity(goToSearchActivity);
                } else {
                    Toast.makeText(NewDinnerActivity.this, getString(R.string.dinner_save_failed), Toast.LENGTH_LONG).show();
                }
            }
        }

        InsertDinner insertDinner = new InsertDinner();
        insertDinner.execute();

    }
}
