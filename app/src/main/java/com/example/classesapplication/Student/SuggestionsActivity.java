package com.example.classesapplication.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.classesapplication.AddTeacherActivity;
import com.example.classesapplication.DashboardActivity;
import com.example.classesapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SuggestionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorWhite));

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorBack));

        Spinner spinner = findViewById(R.id.spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.list, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        text = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void giveFeedback(View view) {
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Float rating = ratingBar.getRating();
        String ratingString = rating.toString();
//        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        EditText editText = findViewById(R.id.suggestions);
        String suggestion = editText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("Student", Context.MODE_PRIVATE);
        String studentName = sharedPreferences.getString("name","");
        addTeacherinFirebase(ratingString,suggestion,text,studentName);
    }

    private void addTeacherinFirebase(String rating, String suggestion, String name, String studentName) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("TeacherName", name);
        hashMap.put("Rating", rating);
        hashMap.put("Suggestions", suggestion);
        hashMap.put("StudentName", studentName);
        long count = System.currentTimeMillis();
        FirebaseDatabase.getInstance().getReference("Admin").child("Teacher Review").child(name+count)
                .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toasty.success(SuggestionsActivity.this, "Thank you for your Review", Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(SuggestionsActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toasty.error(SuggestionsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fDatabaseRoot = database.getReference();

        fDatabaseRoot.child("Teachers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> propertyAddressList = new ArrayList<String>();

                for (DataSnapshot addressSnapshot : dataSnapshot.getChildren()) {
                    String propertyAddress = addressSnapshot.child("TeacherName").getValue(String.class);
                    if (propertyAddress != null) {
                        propertyAddressList.add(propertyAddress);
                    }
                }

                Spinner spinnerProperty = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(SuggestionsActivity.this, android.R.layout.simple_spinner_item, propertyAddressList);
                addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProperty.setAdapter(addressAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}