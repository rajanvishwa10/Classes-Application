package com.example.classesapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    EditText editText, editText2, editText3, editText4;
    CircleImageView circleImageView;
    Uri uri, Imageuri;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        circleImageView = findViewById(R.id.imageView);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorBack));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorBack));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editText = findViewById(R.id.name);
        editText2 = findViewById(R.id.email);
        editText3 = findViewById(R.id.phone);
        editText4 = findViewById(R.id.guardname);

        read();

    }

    public void gotoDashboard(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();

    }

    private void read() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").getValue(String.class);
                    String email = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").getValue(String.class);
                    String number = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("mobile").getValue(String.class);
                    String guardian = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("guardianName").getValue(String.class);
                    String url = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profileImage").getValue(String.class);

                    editText.setText(name);
                    editText2.setText(email);
                    editText3.setText(number);
                    editText4.setText(guardian);
                    Glide.with(ProfileActivity.this).load(url)
                            .addListener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(circleImageView);
                } else {
                    Toast.makeText(ProfileActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Failed to read value
                Toast.makeText(ProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void update(String name, String phoneNo, String guardian, Uri uri) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getUid());

        Map<String, Object> updates = new HashMap<String, Object>();

        updates.put("name", name);
        updates.put("mobile", phoneNo);
        updates.put("guardianName", guardian);
        updates.put("profileImage", String.valueOf(uri));

        ref.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Not Updated", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void update(View view) {

        String name = editText.getText().toString();
        String phone = editText3.getText().toString();
        String guardianName = editText4.getText().toString();

        if (name.isEmpty()) {
            editText.setError("Enter Name");
            editText.requestFocus();
        } else if (phone.isEmpty()) {
            editText3.setError("Enter Phone");
            editText3.requestFocus();
        } else if (guardianName.isEmpty()) {
            editText4.setError("Enter Guardian Name");
            editText4.requestFocus();
        } else {
            update(name, phone, guardianName, Imageuri);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.singout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectApicture(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            uri = data.getData();
            Log.d("ImageUri", "" + uri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                circleImageView.setImageBitmap(bitmap);
                uploadImage(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(Bitmap bitmap) {
        progressBar.setVisibility(View.VISIBLE);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        final StorageReference reference = FirebaseStorage.getInstance().getReference().
                child("ProfileImages").
                child(FirebaseAuth.getInstance().getUid() + ".jpeg");

        reference.putBytes(byteArrayOutputStream.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(
                                new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri Imguri) {
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getUid());

                                        Map<String, Object> updates = new HashMap<String, Object>();

                                        updates.put("profileImage", String.valueOf(Imguri));

                                        ref.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(ProfileActivity.this, "Profile Photo Uploaded", Toast.LENGTH_SHORT).show();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(ProfileActivity.this, "Not Updated", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                        );

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}