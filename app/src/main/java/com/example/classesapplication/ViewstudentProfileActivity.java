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
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorBack));

        TextView textView = findViewById(R.id.name);
        TextView textView2 = findViewById(R.id.email);
        TextView textView3 = findViewById(R.id.studentClass);
        TextView textView4 = findViewById(R.id.phone);
        TextView textView5 = findViewById(R.id.guardname);
        ImageView imageView = findViewById(R.id.imageView);

        textView.setText("Name : "+getIntent().getStringExtra("name"));
        textView2.setText("Email : "+getIntent().getStringExtra("email"));
        textView3.setText("Class : "+getIntent().getStringExtra("class"));
        textView4.setText("Phone : "+getIntent().getStringExtra("phone"));
        textView5.setText("Guardian Name : "+getIntent().getStringExtra("guardian"));

        String image = getIntent().getStringExtra("profilepic");
        Glide.with(this).load(image).into(imageView);
    }
}