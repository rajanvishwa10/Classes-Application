package com.example.classesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.classesapplication.Student.AssignmentsActivity;
import com.example.classesapplication.Teacher.CreateAssignmentsActivity;
import com.example.classesapplication.Teacher.CreateNoticeActivity;
import com.example.classesapplication.Teacher.CreateQuizActivity;
import com.example.classesapplication.Teacher.CreateSuggestionsActivity;
import com.example.classesapplication.Teacher.CreateTimetableActivity;
import com.example.classesapplication.Teacher.SetAttendanceActivity;
import com.google.firebase.auth.FirebaseAuth;

public class TeacherDashboardActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorWhite));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorBack));

        getSupportActionBar().setTitle("Teacher Dashboard");

        SharedPreferences sharedPreferences = getSharedPreferences("teachername", Context.MODE_PRIVATE);
        String teachername = sharedPreferences.getString("teachername", "");

        textView = findViewById(R.id.teachername);
        textView.setText(teachername);

        CardView cardView = findViewById(R.id.cardView);
        CardView cardView2 = findViewById(R.id.cardView2);
        CardView cardView3 = findViewById(R.id.cardView3);
        CardView cardView6 = findViewById(R.id.cardView6);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetAttendanceActivity.class);
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateTimetableActivity.class);
                startActivity(intent);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateNoticeActivity.class);
                startActivity(intent);
            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAssignmentsActivity.class);
                startActivity(intent);
            }
        });
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

            SharedPreferences sharedPreferences = getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}