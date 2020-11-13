package com.example.classesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class CreateNewNoticeActivity extends AppCompatActivity {

    String type = "Students";
    Bitmap bitmap;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_notice);

        getSupportActionBar().setTitle("Create Notice");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorBack));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorWhite));

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Notice Uploading");
        progressDialog.setMessage("Uploading in progress");


        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.students:
                        type = "Students";
                        break;
                    case R.id.teachers:
                        type = "Teachers";
                        break;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage();
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    private void showFileChooser() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(
//                Intent.createChooser(intent, "Select a File to Upload"),
//                1);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageView imageView = findViewById(R.id.imageView);
                    imageView.setImageURI(uri);
                    Log.d("TAG", "File Uri: " + uri.toString());

                }
                break;

            case 0:
                if (resultCode == RESULT_OK) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    ImageView imageView = findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void submit(View view) {

        EditText editText = findViewById(R.id.edittextNotice);
        String notice = editText.getText().toString().trim();
//        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
        AddNotice(bitmap, type, notice);
        progressDialog.show();
    }

    public void selectFile(View view) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        request();
    }

    private void request() {
        if (ContextCompat.checkSelfPermission
                (this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1
            );

        }
    }

    private void SelectImage() {
        final CharSequence[] items = {"Gallery", "Camera", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                } else if (items[i].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select file"), 1);
                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.create().show();
    }

    private void AddNotice(Bitmap bitmap, final String type, final String noticeText) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        final Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault());
        final String formattedDate = df.format(c);


        final long count = System.currentTimeMillis();

        final StorageReference reference = FirebaseStorage.getInstance().getReference().
                child("NoticeImage").child(type).
                child(formattedDate + ".jpeg");

        reference.putBytes(byteArrayOutputStream.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(
                                new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri Imguri) {
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Admin")
                                                .child("Notice").child(type).child(type + count);

                                        Map<String, Object> updates = new HashMap<>();

                                        updates.put("NoticeImage", String.valueOf(Imguri));
                                        updates.put("NoticeText", noticeText);
                                        updates.put("Date", formattedDate);

                                        ref.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                Toasty.success(CreateNewNoticeActivity.this, "Notice Uploaded", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(CreateNewNoticeActivity.this, CreateNoticeActivity.class);
                                                startActivity(intent);

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toasty.error(CreateNewNoticeActivity.this, "Notice Upload fail", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(CreateNewNoticeActivity.this, CreateNoticeActivity.class);
                                                startActivity(intent);
                                            }
                                        });

                                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().
                                                child("Notice").child("Notice" + count);

                                        Map<String, Object> updates2 = new HashMap<>();

                                        updates2.put("NoticeImage", String.valueOf(Imguri));
                                        updates2.put("NoticeText", noticeText);
                                        updates2.put("Date", formattedDate);

                                        ref2.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                Toasty.success(CreateNewNoticeActivity.this, "Notice Uploaded", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(CreateNewNoticeActivity.this, CreateNoticeActivity.class);
                                                startActivity(intent);

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toasty.error(CreateNewNoticeActivity.this, "Notice Upload fail", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(CreateNewNoticeActivity.this, CreateNoticeActivity.class);
                                                startActivity(intent);
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

                        progressDialog.dismiss();
                        Toasty.error(CreateNewNoticeActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}