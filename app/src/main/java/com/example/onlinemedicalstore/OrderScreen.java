package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class OrderScreen extends AppCompatActivity {

    private DatabaseReference orderReference;
    private ArrayList<OrderModel> orderModels;
    private MaterialProgressBar progressbar;
    private FirebaseAuth maAuth;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        progressbar = (MaterialProgressBar) findViewById(R.id.progressbar);
        maAuth = FirebaseAuth.getInstance();
        orderReference = FirebaseDatabase.getInstance().getReference("Orders").child(maAuth.getUid());

        orderModels = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.order_recyclerview);
        orderAdapter = new OrderAdapter(orderModels, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
        getOrderList();

    }

    private void getOrderList() {

        orderReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    orderModels.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        OrderModel orderList = snapshot1.getValue(OrderModel.class);
                        orderModels.add(orderList);
                    }

                    orderAdapter.notifyDataSetChanged();
                    progressbar.setVisibility(View.GONE);
                }
                progressbar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error->getRequestList--" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}