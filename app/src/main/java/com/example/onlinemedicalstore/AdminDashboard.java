package com.example.onlinemedicalstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminDashboard extends AppCompatActivity {

    CardView addCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        addCategory = (CardView) findViewById(R.id.add_categories);

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, AddNewCategory.class);
                //intent.putExtra("Did", model.getDname());
                startActivity(intent);
            }
        });

        // Enables Always-on

    }
}