package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class CartScreen extends AppCompatActivity {

    private DatabaseReference cartReference;
    private ArrayList<CartMedicineModel> cartMedicineModels;
    private CartModel cartModel;
    private TextView totalCartPrice;
    private MaterialProgressBar progressbar;
    private Button checkBtn;
    private FirebaseAuth maAuth;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView emptyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_screen);

        progressbar = (MaterialProgressBar) findViewById(R.id.progressbar);
        maAuth = FirebaseAuth.getInstance();
        checkBtn = (Button) findViewById(R.id.checkout_btn);
        totalCartPrice = (TextView) findViewById(R.id.total_price);
        emptyText = (TextView) findViewById(R.id.empty_tag);


        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartScreen.this, AddressScreen.class);
                startActivity(intent);
            }
        });

        cartReference = FirebaseDatabase.getInstance().getReference("Carts");

        cartMedicineModels = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.cart_item_recyclerview);


        getCartList();


    }


    private void getCartList() {

        cartReference.child(maAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    cartModel = snapshot.getValue(CartModel.class);
                    totalCartPrice.setText("Total Price: Rs " + String.valueOf(cartModel.getCartPrice()));

                    cartMedicineModels.clear();
                    for (DataSnapshot snapshot1 : snapshot.child("Medicines").getChildren()) {
                        CartMedicineModel cartList = snapshot1.getValue(CartMedicineModel.class);
                        cartMedicineModels.add(cartList);
                    }

                    cartAdapter = new CartAdapter(cartMedicineModels, CartScreen.this, cartModel);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CartScreen.this));
                    recyclerView.setAdapter(cartAdapter);

                    cartAdapter.notifyDataSetChanged();
                    progressbar.setVisibility(View.GONE);

                } else {
                    emptyText.setVisibility(View.VISIBLE);
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