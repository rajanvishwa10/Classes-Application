package com.example.classesapplication.Teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.classesapplication.Attendance;
import com.example.classesapplication.AttendanceAdapter;
import com.example.classesapplication.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SetAttendanceActivity extends AppCompatActivity {
    List<Attendance> fetchData;
    RecyclerView recyclerView;
    AttendanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_attendance);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchData = new ArrayList<>();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorBack));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorWhite));

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String date = df.format(c);

        TextView textView = findViewById(R.id.Date);
        textView.setText(date);

        FirebaseRecyclerOptions<Attendance> options =
                new FirebaseRecyclerOptions.Builder<Attendance>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), Attendance.class)
                        .build();

        adapter = new AttendanceAdapter(options);
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