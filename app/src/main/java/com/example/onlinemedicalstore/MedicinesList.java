package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MedicinesList extends AppCompatActivity {

    DatabaseReference medicineReference;
    ArrayList<MedicinesModel> medicinesModel;
    private MedicineAdapter medicineAdapter;
    private String categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines_list);

        categoryId = getIntent().getStringExtra("categoryId");


        medicineReference = FirebaseDatabase.getInstance().getReference("Medicines");


        medicinesModel = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        medicineAdapter = new MedicineAdapter(medicinesModel, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(medicineAdapter);

        getMedicineList();
    }

    private void getMedicineList() {

        medicineReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    medicinesModel.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        MedicinesModel medicineList = snapshot1.getValue(MedicinesModel.class);
                        medicinesModel.add(medicineList);


                    }

                    medicineAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error->getRequestList--" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
