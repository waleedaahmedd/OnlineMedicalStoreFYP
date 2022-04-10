package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class CartScreen extends AppCompatActivity {

    DatabaseReference cartReference;
    ArrayList<CartModel> cartModels;
    MaterialProgressBar progressbar;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        progressbar = (MaterialProgressBar) findViewById(R.id.progressbar);
        cartReference = FirebaseDatabase.getInstance().getReference("Carts").child("7ACHbpbTSvgZMLuW5Qo6XaMWWdn1");

        cartModels = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cart_item_recyclerview);
        cartAdapter = new CartAdapter(cartModels, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);

        getCartList();


    }

    private void getCartList() {

        cartReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        CartModel cartList = snapshot1.getValue(CartModel.class);
                        cartModels.add(cartList);
                    }

                    cartAdapter.notifyDataSetChanged();
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error->getRequestList--" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}