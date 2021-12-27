package com.example.onlinemedicalstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends AppCompatActivity {

    private LinearLayout logOut;
    DatabaseReference categoriesReference;
    ArrayList<CategoriesModel> categoryModels;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        categoriesReference = FirebaseDatabase.getInstance().getReference("Categories");

        logOut = (LinearLayout) findViewById(R.id.logout_btn);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserDashboard.this,MainActivity.class));
                SharedPref.getInstance(getApplicationContext()).setUserType("0",getApplicationContext());
                finish();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        categoryModels = new ArrayList<>();

        categoryAdapter = new CategoryAdapter((ArrayList<CategoriesModel>) categoryModels, UserDashboard.this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getApplicationContext(), 3, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoryAdapter);


        getChildrenList();


       /* CategoryModel[] myListData = new CategoryModel[]{
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
        };*/
        

    }

    private void getChildrenList() {

        categoriesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        CategoriesModel categoriesList = snapshot1.getValue(CategoriesModel.class);
                       categoryModels.add(categoriesList);


                    }

                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error->getRequestList--" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    // Enables Always-on
}
