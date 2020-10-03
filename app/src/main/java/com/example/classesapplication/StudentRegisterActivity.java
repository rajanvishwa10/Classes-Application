package com.example.classesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.classesapplication.ui.home.StudentFragment;

public class StudentRegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
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

        String name = editText.getText().toString().trim();
        String email = editText1.getText().toString().trim();
        String pass = editText2.getText().toString().trim();
        String cpass = editText3.getText().toString().trim();
        String studentClass = editText4.getText().toString().trim();

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
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
        }

    }

    public void loginIntent(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
        finish();
    }
}