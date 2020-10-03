package com.example.classesapplication.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.classesapplication.R;
import com.example.classesapplication.TeacherDashboardActivity;

public class TeacherFragment extends Fragment {
    View view;
    EditText editText, editText2;
    Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher, container, false);
        editText = view.findViewById(R.id.teacherusername);
        editText2 = view.findViewById(R.id.teacherpass);
        button = view.findViewById(R.id.teacherlogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String username = editText.getText().toString();
//                String pass = editText2.getText().toString();
//                if (username.isEmpty()) {
//                    editText.setError("Enter Username");
//                    editText.requestFocus();
//                } else if (pass.isEmpty()) {
//                    editText2.setError("Enter Username");
//                    editText2.requestFocus();
//                } else if (username.equals("rajan") && pass.equals("rajan")) {
//                    //Toast.makeText(getContext(), "Log in successfull", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getContext(), TeacherFragment.class);
//                    startActivity(intent);
//                } else if (username.equals("aniket") && pass.equals("aniket")) {
//                    Toast.makeText(getContext(), "Log in successfull", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getContext(), TeacherFragment.class);
//                    startActivity(intent);
//                }else{
//                    Toast.makeText(getContext(), "Cant login, Get your username and password from admin", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getContext(), TeacherFragment.class);
//                    startActivity(intent);
//                }
                Intent intent = new Intent(getContext().getApplicationContext(), TeacherDashboardActivity.class);
                startActivity(intent);
            }
        });
                return view;
    }
}