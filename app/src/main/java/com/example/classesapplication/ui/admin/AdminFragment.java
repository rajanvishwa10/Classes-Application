package com.example.classesapplication.ui.admin;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.classesapplication.AddTeacherActivity;
import com.example.classesapplication.AdminDashboard;
import com.example.classesapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import es.dmoral.toasty.Toasty;

public class AdminFragment extends Fragment {
    View view;
    EditText editText, editText2;
    Button button;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin, container, false);
        editText = view.findViewById(R.id.username);
        editText2 = view.findViewById(R.id.pass);
        button = view.findViewById(R.id.login3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = editText.getText().toString();
                String pass = editText2.getText().toString();
                if (username.isEmpty()) {
                    editText.setError("Username Empty");
                    editText.requestFocus();
                } else if (pass.isEmpty()) {
                    editText2.setError("Password Empty");
                    editText2.requestFocus();
                } else if (username.equals("admin") && pass.equals("admin")) {
                    Toasty.success(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), AdminDashboard.class);
                    startActivity(intent);

                }else{
                    Toasty.error(getContext(), "Not Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}