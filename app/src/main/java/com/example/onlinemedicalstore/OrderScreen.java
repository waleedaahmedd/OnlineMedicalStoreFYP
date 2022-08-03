package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class OrderScreen extends AppCompatActivity {

    private DatabaseReference orderReference;
    private ArrayList<OrderModel> orderModels;
    private MaterialProgressBar progressbar;
    private FirebaseAuth maAuth;
    private OrderAdapter orderAdapter;
    private TextView emptyText;
    private String comingFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        comingFrom = getIntent().getStringExtra("onClick");


        progressbar = (MaterialProgressBar) findViewById(R.id.progressbar);
        maAuth = FirebaseAuth.getInstance();
        emptyText = (TextView) findViewById(R.id.empty_tag);

        orderReference = FirebaseDatabase.getInstance().getReference("Orders");

        orderModels = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.order_recyclerview);
        orderAdapter = new OrderAdapter(orderModels, this, comingFrom);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
        getOrderList();

    }

    private void getOrderList() {

        if (SharedPref.getInstance(getApplicationContext()).getUserType(getApplicationContext()).toUpperCase().equals("USER")) {
            orderReference.orderByChild("Uid").equalTo(maAuth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        orderModels.clear();
                        for (DataSnapshot keySnap : snapshot.getChildren()) {
                            String key = keySnap.getKey();
                            OrderModel orderList = snapshot.child(key).getValue(OrderModel.class);
                            orderModels.add(orderList);
                            //  emptyText.setText(snapshot.child(key).child("cartPrice").getValue().toString());
                        }
                  /*  for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        OrderModel orderList = snapshot1.getValue(OrderModel.class);
                        orderModels.add(orderList);
                    }*/

                        orderAdapter.notifyDataSetChanged();
                        progressbar.setVisibility(View.GONE);
                    } else {
                        progressbar.setVisibility(View.GONE);
                        emptyText.setVisibility(View.VISIBLE);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Error->getRequestList--" + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        } else if (SharedPref.getInstance(getApplicationContext()).getUserType(getApplicationContext()).toUpperCase().equals("ADMIN") && comingFrom == null){
            orderReference.orderByChild("Status").equalTo("Pending").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        orderModels.clear();
                        for (DataSnapshot keySnap : snapshot.getChildren()) {
                            String key = keySnap.getKey();
                            OrderModel orderList = snapshot.child(key).getValue(OrderModel.class);
                            orderModels.add(orderList);
                            //  emptyText.setText(snapshot.child(key).child("cartPrice").getValue().toString());
                        }
                  /*  for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        OrderModel orderList = snapshot1.getValue(OrderModel.class);
                        orderModels.add(orderList);
                    }*/

                        orderAdapter.notifyDataSetChanged();
                        progressbar.setVisibility(View.GONE);
                    } else {
                        progressbar.setVisibility(View.GONE);
                        emptyText.setText("No Order is in Pending");

                        emptyText.setVisibility(View.VISIBLE);


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Error->getRequestList--" + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
        else {
            orderReference.orderByChild("Status").equalTo("Completed").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        orderModels.clear();
                        for (DataSnapshot keySnap : snapshot.getChildren()) {
                            String key = keySnap.getKey();
                            OrderModel orderList = snapshot.child(key).getValue(OrderModel.class);
                            orderModels.add(orderList);
                            //  emptyText.setText(snapshot.child(key).child("cartPrice").getValue().toString());
                        }
                  /*  for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        OrderModel orderList = snapshot1.getValue(OrderModel.class);
                        orderModels.add(orderList);
                    }*/

                        orderAdapter.notifyDataSetChanged();
                        progressbar.setVisibility(View.GONE);
                    } else {
                        progressbar.setVisibility(View.GONE);
                        emptyText.setText("Sorry No Order is Completed Yet");
                        emptyText.setVisibility(View.VISIBLE);

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Error->getRequestList--" + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }


    }
}