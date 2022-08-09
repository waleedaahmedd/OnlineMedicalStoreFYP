package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class AddressScreen extends AppCompatActivity {

    Button submitBtn;
    DatabaseReference cartReference, orderReference;
    FirebaseAuth maAuth;
    MaterialProgressBar progressBar;
    EditText name, phone, houseNumber, nearLocation;
    String refNumber;
    String receiverName, receiverPhone, receiverHouse, receiverLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_screen);
        maAuth = FirebaseAuth.getInstance();

        submitBtn = (Button) findViewById(R.id.submit_address);
        progressBar = (MaterialProgressBar) findViewById(R.id.progressbar);
        name = (EditText) findViewById(R.id.receiver_name);
        phone = (EditText) findViewById(R.id.receiver_phone);
        houseNumber = (EditText) findViewById(R.id.address);
        nearLocation = (EditText) findViewById(R.id.near_location);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiverName = name.getText().toString().trim();
                receiverPhone = phone.getText().toString().trim();
                receiverHouse = houseNumber.getText().toString().trim();
                receiverLocation = nearLocation.getText().toString().trim();


                if (receiverName.length() == 0 || receiverPhone.length() == 0 || receiverHouse.length() == 0 || receiverLocation.length() == 0) {
                    Toast.makeText(AddressScreen.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    moveToOrder();
                }

            }
        });
    }

    private void moveToOrder() {
        cartReference = FirebaseDatabase.getInstance().getReference("Carts");
        orderReference = FirebaseDatabase.getInstance().getReference("Orders");
        orderReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int size = (int) snapshot.getChildrenCount();
                refNumber = String.valueOf(size + 1);
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault());
                String formattedDate = df.format(c);
                cartReference.child(maAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        orderReference.child(refNumber).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                                orderReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        HashMap<String, Object> addressDataMap = new HashMap<>();
                                        addressDataMap.put("receiverName", receiverName);
                                        addressDataMap.put("receiverPhone", receiverPhone);
                                        addressDataMap.put("receiverHouse", receiverHouse);
                                        addressDataMap.put("receiverLocation", receiverLocation);
                                        orderReference.child(refNumber).child("DeliverDetails").updateChildren(addressDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                orderReference.child(refNumber).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                                                        orderReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                HashMap<String, Object> addressDataMap = new HashMap<>();
                                                                addressDataMap.put("receiverName", receiverName);
                                                                addressDataMap.put("receiverPhone", receiverPhone);
                                                                addressDataMap.put("receiverHouse", receiverHouse);
                                                                addressDataMap.put("receiverLocation", receiverLocation);
                                                                orderReference.child(refNumber).child("DeliverDetails").updateChildren(addressDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        progressBar.setVisibility(View.GONE);
                                                                        Toast.makeText(AddressScreen.this, "Order Submitted Successfully", Toast.LENGTH_SHORT).show();


                                                                      /*  cartReference.child("Medicines").child("-N1G0r7zXUP3QAs_Mvgm").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void unused) {
                                                                              *//*  listData.remove(ListData);
                                                                                adapter.notifyDataSetChanged();*//*
                                                                                *//*   if (listData.size() == 1){

                                                                                 *//**//* ((Activity)context).;*//**//*                        }*//*
                                                                                // listData.remove(ListData);
                                                                            }
                                                                        });*/

                                                                        cartReference.child(maAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                for (DataSnapshot medicineSnapshot: dataSnapshot.getChildren()) {
                                                                                    medicineSnapshot.getRef().removeValue();
                                                                                }
                                                                                finish();
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(DatabaseError databaseError) {
                                                                                Toast.makeText(AddressScreen.this, "Went Wrong", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });


                                                    }
                                                });
                                                orderReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                        HashMap<String, Object> userIdDataMap = new HashMap<>();
                                                        userIdDataMap.put("Uid", maAuth.getUid());
                                                        userIdDataMap.put("Status", "Pending");
                                                        userIdDataMap.put("RefNumber", refNumber);
                                                        userIdDataMap.put("DateAndTime", formattedDate);

                                                        orderReference.child(refNumber).updateChildren(userIdDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                progressBar.setVisibility(View.GONE);
                                                                Toast.makeText(AddressScreen.this, "Order Submitted Successfully", Toast.LENGTH_SHORT).show();

                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });


                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressBar.setVisibility(View.GONE);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}