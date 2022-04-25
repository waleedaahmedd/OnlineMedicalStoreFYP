package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    Button checkBtn;
    FirebaseAuth maAuth;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        progressbar = (MaterialProgressBar) findViewById(R.id.progressbar);
        maAuth = FirebaseAuth.getInstance();
        checkBtn = (Button) findViewById(R.id.checkout_btn);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartScreen.this , AddressScreen.class);
                startActivity(intent);
            }
        });

        cartReference = FirebaseDatabase.getInstance().getReference("Carts").child(maAuth.getUid());

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
                progressbar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error->getRequestList--" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}