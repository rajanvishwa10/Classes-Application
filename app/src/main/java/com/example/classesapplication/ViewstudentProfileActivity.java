package com.example.classesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class ViewstudentProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstudent_profile);

        getSupportActionBar().setTitle("Student Profile");

        TextView textView = findViewById(R.id.name);
        TextView textView2 = findViewById(R.id.email);
        TextView textView3 = findViewById(R.id.studentClass);
        TextView textView4 = findViewById(R.id.phone);
        TextView textView5 = findViewById(R.id.guardname);
        ImageView imageView = findViewById(R.id.imageView);

        textView.setText(getIntent().getStringExtra("name"));
        textView2.setText(getIntent().getStringExtra("email"));
        textView3.setText(getIntent().getStringExtra("class"));
        textView4.setText(getIntent().getStringExtra("phone"));
        textView5.setText(getIntent().getStringExtra("guardian"));

        String image = getIntent().getStringExtra("profilepic");
        Glide.with(this).load(image).into(imageView);
    }
}