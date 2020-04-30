package com.example.mydinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button addNewDinnerBtn =  findViewById(R.id.add_dinner_button);

        addNewDinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToNewDinnerActivity = new Intent(SearchActivity.this, NewDinnerActivity.class);
                startActivity(goToNewDinnerActivity);

            }
        });

    }
}
