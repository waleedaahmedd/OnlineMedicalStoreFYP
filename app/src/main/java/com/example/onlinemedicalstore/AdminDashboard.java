package com.example.onlinemedicalstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {

    CardView addCategory, addMedicines, editCategory, editMedicine;
    private TextView logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        logOut = (TextView) findViewById(R.id.logout_btn);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminDashboard.this,MainActivity.class));
                SharedPref.getInstance(getApplicationContext()).setUserType("0",getApplicationContext());
                finish();

            }
        });

        addCategory = (CardView) findViewById(R.id.add_categories);
        editCategory = (CardView) findViewById(R.id.edit_categories);
        addMedicines = (CardView) findViewById(R.id.add_medicines);
        editMedicine = (CardView) findViewById(R.id.edit_medicines);



        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, AddNewCategory.class);
                intent.putExtra("onClick", "addNewCat");
                startActivity(intent);
            }
        });

        editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, CategoriesList.class);
                intent.putExtra("onClick", "updateCat");
                startActivity(intent);
            }
        });


        addMedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, CategoriesList.class);
                intent.putExtra("onClick", "addNewMed");
                startActivity(intent);
            }
        });

        editMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, MedicinesList.class);
                intent.putExtra("onClick", "updateMed");
                startActivity(intent);
            }
        });

        // Enables Always-on

    }
}