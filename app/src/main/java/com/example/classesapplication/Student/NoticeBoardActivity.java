package com.example.classesapplication.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.classesapplication.R;

public class NoticeBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorBack));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorBack));
    }
}