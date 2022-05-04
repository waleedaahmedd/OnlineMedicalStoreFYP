package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoriesList extends AppCompatActivity {

    private DatabaseReference categoriesReference;
    private ArrayList<CategoriesModel> categoryModels;
    private CategoryAdapter categoryAdapter;
    private String  comingFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);
        categoriesReference = FirebaseDatabase.getInstance().getReference("Categories");

        comingFrom = getIntent().getStringExtra("onClick");

        categoryModels = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.category_list_recyclerView);

        categoryAdapter = new CategoryAdapter((ArrayList<CategoriesModel>) categoryModels, CategoriesList.this, "Categories", comingFrom);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getApplicationContext(), 3, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoryAdapter);


        getCategoryList();

    }


    private void getCategoryList() {

        categoriesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    categoryModels.clear();
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
}