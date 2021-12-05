package com.example.onlinemedicalstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

public class UserDashboard extends AppCompatActivity {

    private TextView logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        logOut = (TextView) findViewById(R.id.logout_btn);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserDashboard.this,MainActivity.class));
                SharedPref.getInstance(getApplicationContext()).setUserType("0",getApplicationContext());
                finish();
            }
        });


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

        CategoryAdapter adapter = new CategoryAdapter(myListData , this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getApplicationContext(), 3, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    // Enables Always-on
}
