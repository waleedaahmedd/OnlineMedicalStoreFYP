package com.example.onlinemedicalstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MedicineDetail extends AppCompatActivity {

    private MedicinesModel medicineDetail;
    private ImageView image;
    private TextView name , price, description, discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        medicineDetail = (MedicinesModel) getIntent().getSerializableExtra("medicineData");

        name = (TextView) findViewById(R.id.detail_title);
        description = (TextView) findViewById(R.id.detail_description);
        price = (TextView) findViewById(R.id.detail_newprice);
        discount = (TextView) findViewById(R.id.detail_dicount);
        image = (ImageView) findViewById(R.id.detail_image);


        name.setText(medicineDetail.getName());
        discount.setText(medicineDetail.getDiscount());
        description.setText(medicineDetail.getDescription());
        price.setText(medicineDetail.getPrice());
        Picasso.get()
                .load(medicineDetail.getImage())
                .into(image);



    }
}