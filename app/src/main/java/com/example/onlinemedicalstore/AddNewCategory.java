package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class AddNewCategory extends AppCompatActivity {

    private ImageView categoryImage;
    private Uri imageUri;
    private String checker = "";
    private Button createCategory;
    private EditText categoryName;
    private ProgressDialog loadingBar;
    private StorageTask uploadTask;
    private String myUrl = "";
    private DatabaseReference RootRef;
    private CategoriesModel categoryData;
    private StorageReference storageCategoryImageRef;
    private String catId;
    private String comingFrom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_category);

        storageCategoryImageRef = FirebaseStorage.getInstance().getReference().child("Category pictures");
        RootRef = FirebaseDatabase.getInstance().getReference().child("Categories");


        comingFrom = getIntent().getStringExtra("onClick");


        categoryImage = (ImageView) findViewById(R.id.category_image);
        createCategory = (Button) findViewById(R.id.btn_create_category);
        categoryName = (EditText) findViewById(R.id.category_name);
        loadingBar = new ProgressDialog(this);

        if (!comingFrom.equals("addNewCat")) {
        getCategoryData();
        }


        categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(AddNewCategory.this);
            }
        });

        createCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checker.equals("clicked")) {
                    Validation();
                } else {

                    Toast.makeText(AddNewCategory.this, "Please Insert Employee Picture.", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    private void getCategoryData() {
        RootRef.child(comingFrom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    categoryData = snapshot.getValue(CategoriesModel.class);

                    categoryName.setText(categoryData.getName());
                    Picasso.get()
                            .load(categoryData.getImage())
                            .into(categoryImage);
                    createCategory.setText("Update Category");
                   /* for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        CategoriesModel categoriesList = snapshot1.getValue(CategoriesModel.class);
                        categoryModels.add(categoriesList);
                    }*/

                  /*  categoryAdapter.notifyDataSetChanged();*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error->getRequestList--" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void Validation() {
        String name = categoryName.getText().toString();


        if (TextUtils.isEmpty(name)) {

            Toast.makeText(AddNewCategory.this, "Please category name", Toast.LENGTH_SHORT).show();
        } else {
         /*   loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();*/

            CategoryUpdate(name);

        }
    }

    private void CategoryUpdate(final String name
    ) {

        loadingBar = new ProgressDialog(this);
        loadingBar.setTitle("Update Category");
        loadingBar.setMessage("Please wait, while we are updating category list");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        if (imageUri != null) {

            final StorageReference fileRef = storageCategoryImageRef
                    .child(name + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }


                    return fileRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUrl = task.getResult();
                                myUrl = downloadUrl.toString();

                                loadingBar.dismiss();

                                if (!comingFrom.equals("addNewCat")) {
                                    catId = comingFrom;

                                } else {
                                    catId = RootRef.push().getKey();

                                }

                                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (!(dataSnapshot.child("Categories").child(name).exists())) {

                                            HashMap<String, Object> userdataMap = new HashMap<>();
                                            userdataMap.put("name", name);
                                            userdataMap.put("image", myUrl);
                                            userdataMap.put("id", catId);
                                            RootRef.child(catId).updateChildren(userdataMap)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()) {

                                                               /* HashMap<String, Object> userdataMap = new HashMap<>();
                                                                userdataMap.put("name", name);
                                                                userdataMap.put("image", myUrl);*/

                                                                loadingBar.dismiss();
                                                                finish();



                                                                /*RootRef1.child("Departments").child(departmentID).child("Employees").child(phone).updateChildren(userdataMap)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                if (task.isSuccessful()){

                                                                                    Toast.makeText(AddNewEmployee.this,"Congratulation employ has been created.",Toast.LENGTH_SHORT).show();
                                                                                    loadingBar.dismiss();

                                                                                    Intent Intent = new Intent(AddNewEmployee.this, AdminHomeActivity.class);
                                                                                    startActivity(Intent);
                                                                                    finish();
                                                                                }

                                                                                else
                                                                                {

                                                                                    loadingBar.dismiss();
                                                                                    Toast.makeText(AddNewEmployee.this,"Network Error: Please try again after some time.",Toast.LENGTH_SHORT).show();

                                                                                }

                                                                            }
                                                                        });*/


                                                            } else {

                                                                Toast.makeText(AddNewCategory.this, "Try Again", Toast.LENGTH_SHORT).show();
                                                                loadingBar.dismiss();
                                                                /*Toast.makeText(AddNewEmployee.this,"Please try again using another phone number",Toast.LENGTH_SHORT).show();
                                                                 */
                                                                Intent Intent = new Intent(AddNewCategory.this, AddNewCategory.class);
                                                                startActivity(Intent);
                                                            }


                                                        }
                                                    });
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }


                        }
                    });
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            categoryImage.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

            /*startActivity(new Intent(AddNewCategory.this, AddNewCategory.class));
            finish();*/
        }


    }
}