package com.example.classesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.classesapplication.Student.Student;
import com.example.classesapplication.ui.home.StudentFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class StudentRegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Student Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorBack));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorBack));
    }

    public void Register(View view) {
        EditText editText, editText1, editText2, editText3, editText4;
        editText = findViewById(R.id.studentName);
        editText1 = findViewById(R.id.studentemail);
        editText2 = findViewById(R.id.studentpass);
        editText3 = findViewById(R.id.studentconfirmpass);
        editText4 = findViewById(R.id.studentClass);

        final String name = editText.getText().toString().trim();
        final String email = editText1.getText().toString().trim();
        String pass = editText2.getText().toString().trim();
        String cpass = editText3.getText().toString().trim();
        final String studentClass = editText4.getText().toString().trim();

        if (name.isEmpty()) {
            editText.setError("Enter Name");
            editText.requestFocus();
        } else if (email.isEmpty()) {
            editText1.setError("Enter email");
            editText1.requestFocus();
        } else if (studentClass.isEmpty()) {
            editText4.setError("Enter Class");
            editText4.requestFocus();
        } else if (pass.isEmpty() || pass.length() < 6) {
            editText2.setError("Password must be greater than 6");
            editText2.requestFocus();
        } else if (!pass.equals(cpass)) {
            editText3.setError("Password should match");
            editText3.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                //mAuth.getCurrentUser();
                                Student student = new Student(name, email, studentClass,"","");
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            finish();
//                                            Intent intent = new Intent(StudentRegisterActivity.this, MainActivity2.class);
//                                            startActivity(intent);

                                            Toasty.success(StudentRegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toasty.error(StudentRegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {

                                Toasty.error(StudentRegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


//            Intent intent = new Intent(this, MainActivity2.class);
//            startActivity(intent);
//            Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
        }

    }

    public void loginIntent(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
        finish();
    }
}