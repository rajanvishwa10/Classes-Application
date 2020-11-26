package com.example.classesapplication.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class StudentFragment extends Fragment {
    View view;
    EditText editText, editText2;
    Button button;
    TextView textView;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_student, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Checking for user");
        progressDialog.setCancelable(false);

        editText = view.findViewById(R.id.studentemail);
        editText2 = view.findViewById(R.id.studentpass);
        textView = view.findViewById(R.id.text);
        mAuth = FirebaseAuth.getInstance();

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
                progressDialog.show();
                if (email.isEmpty()) {
                    editText.setError("Enter Email");
                    editText.requestFocus();
                } else if (pass.isEmpty()) {
                    editText2.setError("Enter Password");
                    editText2.requestFocus();
                } else {
                    login(email, pass);
                }
                // Toast.makeText(getContext(), email, Toast.LENGTH_SHORT).show();

            }
        });
        return view;

    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toasty.success(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), DashboardActivity.class);
                            startActivity(intent);

                        } else {
                            progressDialog.dismiss();
                            Toasty.error(getContext(), "Login Failed, Check your Email and Password", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getContext(), DashboardActivity.class);
            startActivity(intent);

        }
    }
}