package com.example.classesapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class AddTeacherActivity extends AppCompatActivity {
    EditText editText, editText1;
    ProgressBar progressBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        getSupportActionBar().setTitle("Add Teacher");
        editText = findViewById(R.id.teachername);
        editText1 = findViewById(R.id.teacherpass);
        button = findViewById(R.id.addTeacher);

        progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);

    }

    public void addTeacher(View view) {

        String teacherName = editText.getText().toString();
        String teacherPass = editText1.getText().toString();

        if (teacherName.isEmpty()) {
            editText.setError("Enter teachers Name");
            editText.requestFocus();
        } else if (teacherPass.isEmpty()) {
            editText1.setError("Enter teachers Password");
            editText1.requestFocus();
        } else {
            checkifUserPresent(teacherName, teacherPass);
            progressBar.setVisibility(View.VISIBLE);
            button.setText(null);
        }
    }

    private void checkifUserPresent(final String teacherName, final String teacherPass) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Teachers");
        myRef.orderByChild("TeacherName").equalTo(teacherName).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    button.setText("Add Teacher");
                    progressBar.setVisibility(View.GONE);
                    Toasty.success(AddTeacherActivity.this, "Teacher already Present", Toast.LENGTH_LONG).show();
                } else {
                    addTeacherinFirebase(teacherName, teacherPass);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toasty.error(AddTeacherActivity.this, "Database error", Toasty.LENGTH_LONG).show();

            }
        });
    }


    private void addTeacherinFirebase(String name, String pass) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("TeacherName", name);
        hashMap.put("TeacherPass", pass);
        FirebaseDatabase.getInstance().getReference("Teachers").child(name)
                .setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    editText.setText(null);
                    editText1.setText(null);
                    button.setText("Add Teacher");

                    Toasty.success(AddTeacherActivity.this, "Teacher Added", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toasty.error(AddTeacherActivity.this, "Teacher Adding failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}