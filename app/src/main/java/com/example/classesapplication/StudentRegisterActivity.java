package com.example.classesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.classesapplication.Student.Student;
import com.example.classesapplication.ui.home.StudentFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class StudentRegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    Spinner spinner;
    ArrayAdapter<String> classArray;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        mAuth = FirebaseAuth.getInstance();
        spinner = findViewById(R.id.textinputclass);
        spinner.setOnItemSelectedListener(this);

        final List<String> propertyAddressList = new ArrayList<String>();
        propertyAddressList.add("1");
        propertyAddressList.add("2");
        propertyAddressList.add("3");
        propertyAddressList.add("4");
        propertyAddressList.add("5");
        propertyAddressList.add("6");
        propertyAddressList.add("7");
        propertyAddressList.add("8");
        propertyAddressList.add("9");
        propertyAddressList.add("10");

        classArray = new ArrayAdapter<String>(StudentRegisterActivity.this,
                android.R.layout.simple_spinner_item, propertyAddressList);
        classArray.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(classArray);

        getSupportActionBar().setTitle("Student Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorBack));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorWhite));
    }

    public void Register(View view) {
        EditText editText, editText1, editText2, editText3;

        editText = findViewById(R.id.studentName);
        editText1 = findViewById(R.id.studentemail);
        editText2 = findViewById(R.id.studentpass);
        editText3 = findViewById(R.id.studentconfirmpass);

        final String name = editText.getText().toString().trim();
        final String email = editText1.getText().toString().trim();
        String pass = editText2.getText().toString().trim();
        String cpass = editText3.getText().toString().trim();

        if (name.isEmpty()) {
            editText.setError("Enter Name");
            editText.requestFocus();
        } else if (email.isEmpty()) {
            editText1.setError("Enter email");
            editText1.requestFocus();
        }  else if (pass.isEmpty() || pass.length() < 6) {
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

                                Student student = new Student(name, email, data,"","");
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        data = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}