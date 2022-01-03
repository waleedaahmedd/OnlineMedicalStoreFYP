package com.example.onlinemedicalstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class AddNewMedicine extends AppCompatActivity {

    private ImageView medicineImage;
    private Uri imageUri;
    private String checker = "";
    private Button createMedicine;
    private EditText medicineName, medicineDiscount, medicinePrice, medicineDescription;
    private ProgressDialog loadingBar;
    private StorageTask uploadTask;
    private String myUrl = "";
    private StorageReference storageMedicineImageRef;
    private String categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_medicine);

        categoryId = getIntent().getStringExtra("categoryId");

        storageMedicineImageRef = FirebaseStorage.getInstance().getReference().child("Medicine pictures");

        medicineImage = (ImageView) findViewById(R.id.medicine_image);
        createMedicine = (Button) findViewById(R.id.btn_create_medicine);
        medicineName = (EditText) findViewById(R.id.medicine_name);
        medicinePrice = (EditText) findViewById(R.id.medicine_price);
        medicineDiscount = (EditText) findViewById(R.id.medicine_discount);
        medicineDescription = (EditText) findViewById(R.id.medicine_description);
        loadingBar = new ProgressDialog(this);

        medicineImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(AddNewMedicine.this);
            }
        });

        createMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checker.equals("clicked")) {
                    CreateMedicine();
                } else {

                    Toast.makeText(AddNewMedicine.this, "Please Insert Medicine Image.", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void CreateMedicine() {
        String name = medicineName.getText().toString();
        String price = medicinePrice.getText().toString();
        String description = medicineDescription.getText().toString();
        String discount = medicineDiscount.getText().toString();

        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(price) && TextUtils.isEmpty(description)) {

            Toast.makeText(AddNewMedicine.this, "Please insert medicine details", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Create Medicine");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateMedicineName(name, price, description, discount);

        }


    }

    private void ValidateMedicineName(final String name, final String price, final String description, final String discount
    ) {

        //final ProgressDialog progressDialog = new ProgressDialog(this);
        loadingBar.setTitle("Update Profile");
        loadingBar.setMessage("Please wait, while we are updating your account information");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        if (imageUri != null) {

            final StorageReference fileRef = storageMedicineImageRef
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


                                final DatabaseReference RootRef1;
                                RootRef1 = FirebaseDatabase.getInstance().getReference().child("Medicines");
                                String id = RootRef1.push().getKey();

                                RootRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (!(dataSnapshot.child("Medicines").child(name).exists())) {

                                            HashMap<String, Object> userdataMap = new HashMap<>();
                                            userdataMap.put("name", name);
                                            userdataMap.put("price", price);
                                            userdataMap.put("discount", discount);
                                            userdataMap.put("description", description);
                                            userdataMap.put("image", myUrl);
                                            userdataMap.put("categoryId", categoryId);
                                            userdataMap.put("medicineId", id);
                                            RootRef1.child(id).updateChildren(userdataMap)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()) {

                                                               /* HashMap<String, Object> userdataMap = new HashMap<>();
                                                                userdataMap.put("name", name);
                                                                userdataMap.put("price", price);
                                                                userdataMap.put("discount", discount);
                                                                userdataMap.put("description", description);
                                                                userdataMap.put("image", myUrl);*/
                                                                loadingBar.dismiss();


                                                            } else {

                                                                Toast.makeText(AddNewMedicine.this, "Try Again", Toast.LENGTH_SHORT).show();
                                                                loadingBar.dismiss();
                                                                /*Toast.makeText(AddNewEmployee.this,"Please try again using another phone number",Toast.LENGTH_SHORT).show();
                                                                 */
                                                                Intent Intent = new Intent(AddNewMedicine.this, AddNewMedicine.class);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            medicineImage.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

            /*startActivity(new Intent(AddNewCategory.this, AddNewCategory.class));
            finish();*/
        }


    }
}