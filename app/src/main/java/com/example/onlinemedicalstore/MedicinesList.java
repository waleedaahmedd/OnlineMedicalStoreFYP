package com.example.onlinemedicalstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MedicinesList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines_list);

        CategoryModel[] myListData = new CategoryModel[]{
                new CategoryModel("Email", android.R.drawable.ic_dialog_email),
                new CategoryModel("Info", android.R.drawable.ic_dialog_info),
                new CategoryModel("Delete", android.R.drawable.ic_delete),
                new CategoryModel("Dialer", android.R.drawable.ic_dialog_dialer),
                new CategoryModel("Alert", android.R.drawable.ic_dialog_alert),
                new CategoryModel("Map", android.R.drawable.ic_dialog_map),
                new CategoryModel("Email", android.R.drawable.ic_dialog_email),
                new CategoryModel("Info", android.R.drawable.ic_dialog_info),
                new CategoryModel("Delete", android.R.drawable.ic_delete),
                new CategoryModel("Dialer", android.R.drawable.ic_dialog_dialer),
                new CategoryModel("Alert", android.R.drawable.ic_dialog_alert),
                new CategoryModel("Map", android.R.drawable.ic_dialog_map),
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MedicineAdapter adapter = new MedicineAdapter(myListData , this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    // Enables Always-on
    }
