package com.example.classesapplication.Student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.classesapplication.AssignmentAdapter;
import com.example.classesapplication.Assignments;
import com.example.classesapplication.Attendance;
import com.example.classesapplication.Attendance2;
import com.example.classesapplication.R;
import com.example.classesapplication.StudentAttendanceAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AttendanceActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentAttendanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorBack));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorBack));

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this);
        LayoutManager.setReverseLayout(true);
        LayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(LayoutManager);

        FirebaseRecyclerOptions<Attendance2> options =
                new FirebaseRecyclerOptions.Builder<Attendance2>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Student Attendance"), Attendance2.class)
                        .build();

        adapter = new StudentAttendanceAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        adapter.stopListening();
    }
}