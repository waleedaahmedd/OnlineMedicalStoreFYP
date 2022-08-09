package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class OrderDetailScreen extends AppCompatActivity {
    TextView refNumber, RecName, TeleNumber, Address, NearbyLoc;
    private RecyclerView recyclerView;
    private MaterialProgressBar progressbar;
    private DeliveryDetailModel deliveryDetailModel;
    private DatabaseReference deliveryDetailsReference;
    private ArrayList<OrderMedicineModel> medicinesModel;
    private OrderMedicineAdapter orderMedicineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_screen);

        String refValue = getIntent().getStringExtra("RefNumber");

        progressbar = (MaterialProgressBar) findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) findViewById(R.id.order_detail_recyclerview);
        refNumber = (TextView) findViewById(R.id.ref_number);
        RecName = (TextView) findViewById(R.id.rec_name);
        TeleNumber = (TextView) findViewById(R.id.rec_telnumber);
        Address = (TextView) findViewById(R.id.rec_address);
        NearbyLoc = (TextView) findViewById(R.id.rec_nearby);
        medicinesModel = new ArrayList<>();

        orderMedicineAdapter = new OrderMedicineAdapter(medicinesModel, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderMedicineAdapter);

        refNumber.setText("Ref Number: "+refValue);


        deliveryDetailsReference = FirebaseDatabase.getInstance().getReference("Orders").child(refValue);

        getDeliveryDetails();

    }

    private void getDeliveryDetails() {

        deliveryDetailsReference.child("DeliverDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    deliveryDetailModel = snapshot.getValue(DeliveryDetailModel.class);
                    RecName.setText(deliveryDetailModel.getReceiverName());
                    TeleNumber.setText(deliveryDetailModel.getReceiverPhone());
                    Address.setText(deliveryDetailModel.getReceiverHouse());
                    NearbyLoc.setText(deliveryDetailModel.getReceiverLocation());
                    deliveryDetailsReference.child("Medicines").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                medicinesModel.clear();
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    OrderMedicineModel medicineList = snapshot1.getValue(OrderMedicineModel.class);
                                    medicinesModel.add(medicineList);
                                }

                                orderMedicineAdapter.notifyDataSetChanged();
                                progressbar.setVisibility(View.GONE);
                            } else {
                                progressbar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Error->getRequestList--" + error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                    progressbar.setVisibility(View.GONE);
                } else {
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