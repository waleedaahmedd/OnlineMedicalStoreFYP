package com.example.onlinemedicalstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class AddressScreen extends AppCompatActivity {

    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_screen);

        submitBtn = (Button) findViewById(R.id.submit_address);
    }
}