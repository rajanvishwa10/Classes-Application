package com.example.classesapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.classesapplication.DashboardActivity;
import com.example.classesapplication.R;
import com.example.classesapplication.StudentRegisterActivity;

public class StudentFragment extends Fragment {
    View view;
    EditText editText, editText2;
    Button button;
    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_student, container, false);
        editText = view.findViewById(R.id.studentemail);
        editText2 = view.findViewById(R.id.studentpass);
        textView = view.findViewById(R.id.text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StudentRegisterActivity.class);
                startActivity(intent);

            }
        });

        button = view.findViewById(R.id.studentlogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editText.getText().toString();
                String pass = editText2.getText().toString();
                Toast.makeText(getContext(), email, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
        return view;


    }
}