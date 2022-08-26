package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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

import org.w3c.dom.Text;

import java.util.HashMap;

public class MedicineDetail extends AppCompatActivity {

    private MedicinesModel medicineDetail;
    private ImageView image;
    private Button addToCart;
    private LinearLayout discountDetail;
    private TextView name , price, description, discount, oldPrice, quantity, unit, manufacturer;
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
        oldPrice = (TextView) findViewById(R.id.detail_oldPrice);
        discount = (TextView) findViewById(R.id.detail_dicount);
        quantity = (TextView) findViewById(R.id.detail_qty);
        unit = (TextView) findViewById(R.id.detail_unit);
        manufacturer = (TextView) findViewById(R.id.detail_manufacture);

        image = (ImageView) findViewById(R.id.detail_image);
        discountDetail = (LinearLayout) findViewById(R.id.price_discount);
        addToCart = (Button) findViewById(R.id.add_to_cart);
        loadingBar = new ProgressDialog(this);
        maAuth = FirebaseAuth.getInstance();
        qtyButton = (ElegantNumberButton) findViewById(R.id.elegant_btn);

        if (medicineDetail.getDiscount().equals(""))
        {
            discountDetail.setVisibility(View.GONE);
        }



        name.setText(medicineDetail.getName());
        discount.setText("Discount: "+medicineDetail.getDiscount()+"%");
        description.setText(medicineDetail.getDescription());
        price.setText("Rs " +medicineDetail.getPrice());
        quantity.setText(medicineDetail.getQuantity());
        unit.setText(medicineDetail.getUnit());
        manufacturer.setText("(By "+medicineDetail.getManufacturer()+")");
        oldPrice.setText((Html.fromHtml("<strike>" + "Rs "+medicineDetail.getOldPrice() + "</strike>")));
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

                        if (!(dataSnapshot.child(id).child("Medicines").hasChild(medicineDetail.getMedicineId()))) {

                            if (dataSnapshot.child(id).hasChild("cartPrice"))
                            {
                                cartTotalPrice = Integer.parseInt(dataSnapshot.child(id).child("cartPrice").getValue().toString()) + totalPrice;
                            }
                            else{
                                cartTotalPrice = totalPrice;

                            }

                            HashMap<String, Object> cartDataMap = new HashMap<>();
                            cartDataMap.put("medicineId", medicineDetail.getMedicineId());
                            cartDataMap.put("name", medicineDetail.getName());
                            cartDataMap.put("discount", medicineDetail.getDiscount());
                            cartDataMap.put("quantity", medicineDetail.getQuantity());
                            cartDataMap.put("image", medicineDetail.getImage());
                            cartDataMap.put("itemQuantity", qtyButton.getNumber());
                            cartDataMap.put("unit", medicineDetail.getUnit());
                            cartDataMap.put("price", totalPrice);
                            cartDataMap.put("originalPrice", String.valueOf((int) Math.round(medicineDetail.getOldPrice())));
                            RootRef1.child(id).child("Medicines").child(medicineDetail.getMedicineId()).updateChildren(cartDataMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(MedicineDetail.this, medicineDetail.getName()+" added Successfully", Toast.LENGTH_SHORT).show();


                                            if (task.isSuccessful()) {


                                                HashMap<String, Object> pricedataMap = new HashMap<>();
                                                pricedataMap.put("cartPrice", cartTotalPrice);

                                                RootRef1.child(id).updateChildren(pricedataMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if (task.isSuccessful()) {

                                                                    loadingBar.dismiss();
                                                                    Intent Intent = new Intent(MedicineDetail.this, CartScreen.class);
                                                                    startActivity(Intent);
                                                                } else {
                                                                    Toast.makeText(MedicineDetail.this, "Try Again", Toast.LENGTH_SHORT).show();
                                                                    loadingBar.dismiss();
                                                                  //  Toast.makeText(AddNewEmployee.this,"Please try again using another phone number",Toast.LENGTH_SHORT).show();


                                                                }


                                                            }
                                                        });
/*
                                                loadingBar.dismiss();
                                                Intent Intent = new Intent(MedicineDetail.this, CartScreen.class);
                                                startActivity(Intent);*/
                                            } else {
                                                Toast.makeText(MedicineDetail.this, "Try Again", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                               // Toast.makeText(AddNewEmployee.this,"Please try again using another phone number",Toast.LENGTH_SHORT).show();


                                            }


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