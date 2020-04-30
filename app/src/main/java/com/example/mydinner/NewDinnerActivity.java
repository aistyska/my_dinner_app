package com.example.mydinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
                    // converts input string value to double
                    double Price = Double.parseDouble(inputDinnerPrice);

                    Dinner dinner = new Dinner(soup, mainDish, salad, inputDinnerName, Price, deliverable, paymentType);

                    Toast.makeText(NewDinnerActivity.this,
                            getResources().getString(R.string.add_dinner_type) + ": " +  dinner.getDishTypes() + "\n" +
                                    getResources().getString(R.string.add_dinner_name) + ": " + dinner.getDishName() + "\n" +
                                    getResources().getString(R.string.add_dinner_price) + ": " + dinner.getPrice() + "\n" +
                                    getResources().getString(R.string.add_dinner_delivery) + ": " + radioDeliveryValue + "\n" +
                                    getResources().getString(R.string.add_dinner_payment) + ": " + dinner.getPaymentType(),
                            Toast.LENGTH_LONG).show();

                    Intent goToSearchActivity = new Intent(NewDinnerActivity.this, SearchActivity.class);
                    startActivity(goToSearchActivity);
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
}
