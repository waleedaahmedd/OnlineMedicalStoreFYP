package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class MedicineDetail extends AppCompatActivity {

    private MedicinesModel medicineDetail;
    private ImageView image;
    private Button addToCart;
    private TextView name , price, description, discount;
    private ProgressDialog loadingBar;
    FirebaseAuth maAuth;
    private ElegantNumberButton qtyButton;
    int cartTotalPrice;




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
        addToCart = (Button) findViewById(R.id.add_to_cart);
        loadingBar = new ProgressDialog(this);
        maAuth = FirebaseAuth.getInstance();
        qtyButton = (ElegantNumberButton) findViewById(R.id.elegant_btn);



        name.setText(medicineDetail.getName());
        discount.setText(medicineDetail.getDiscount());
        description.setText(medicineDetail.getDescription());
        price.setText(medicineDetail.getPrice());
        Picasso.get()
                .load(medicineDetail.getImage())
                .into(image);


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference RootRef1;
                RootRef1 = FirebaseDatabase.getInstance().getReference().child("Carts");
                String id = maAuth.getCurrentUser().getUid();
                int totalPrice = Integer.parseInt(medicineDetail.getPrice()) * Integer.parseInt(qtyButton.getNumber()) ;

                RootRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (!(dataSnapshot.child("Carts").child(medicineDetail.getMedicineId()).exists())) {

                        /*    if (dataSnapshot.child("Carts").child(id).child("cartPrice").exists())
                            {
                                cartTotalPrice = Integer.parseInt(dataSnapshot.child("Carts").child(id).child("cartPrice").getValue().toString()) * 2;
                            }
                            else{
                                cartTotalPrice = 5 * 5;

                            }*/

                            HashMap<String, Object> cartdataMap = new HashMap<>();
                            cartdataMap.put("medicineId", medicineDetail.getMedicineId());
                           // userdataMap.put("userId", maAuth.getCurrentUser().getUid());
                            cartdataMap.put("quantity", qtyButton.getNumber());
                            cartdataMap.put("price", totalPrice);
                            RootRef1.child(id).child(medicineDetail.getMedicineId()).updateChildren(cartdataMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(MedicineDetail.this, medicineDetail.getName()+" added Successfully", Toast.LENGTH_SHORT).show();


                                       /*     if (task.isSuccessful()) {


                                                HashMap<String, Object> pricedataMap = new HashMap<>();
                                                pricedataMap.put("cartPrice", cartTotalPrice);

                                                RootRef1.child(id).updateChildren(pricedataMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if (task.isSuccessful()) {

                                                                   *//* loadingBar.dismiss();
                                                                    Intent Intent = new Intent(MedicineDetail.this, CartScreen.class);
                                                                    startActivity(Intent);*//*
                                                                } else {
                                                                    Toast.makeText(MedicineDetail.this, "Try Again", Toast.LENGTH_SHORT).show();
                                                                    loadingBar.dismiss();
                                                                    *//*Toast.makeText(AddNewEmployee.this,"Please try again using another phone number",Toast.LENGTH_SHORT).show();
                                                                     *//*

                                                                }


                                                            }
                                                        });

                                                loadingBar.dismiss();
                                                Intent Intent = new Intent(MedicineDetail.this, CartScreen.class);
                                                startActivity(Intent);
                                            } else {
                                                Toast.makeText(MedicineDetail.this, "Try Again", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                                *//*Toast.makeText(AddNewEmployee.this,"Please try again using another phone number",Toast.LENGTH_SHORT).show();
                                                 *//*

                                            }*/


                                        }
                                    });
                        }

                        else{
                            Toast.makeText(MedicineDetail.this, "Already Added into cart", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    }
}