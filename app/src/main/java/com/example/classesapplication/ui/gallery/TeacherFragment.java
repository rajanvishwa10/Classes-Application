package com.example.classesapplication.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.classesapplication.AddTeacherActivity;
import com.example.classesapplication.MainActivity;
import com.example.classesapplication.MainActivity2;
import com.example.classesapplication.R;
import com.example.classesapplication.TeacherDashboardActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

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
                String username = editText.getText().toString();
                String pass = editText2.getText().toString();
                if (username.isEmpty()) {
                    editText.setError("Enter Username");
                    editText.requestFocus();
                } else if (pass.isEmpty()) {
                    editText2.setError("Enter Username");
                    editText2.requestFocus();
                } else {
                    checkifUserPresent(username, pass);
//
                }
//
            }
        });
        return view;
    }


    private void checkifUserPresent(final String teacherName, final String teacherPass) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query myRef = database.getReference("Teachers");
        myRef.orderByChild("TeacherName").equalTo(teacherName)
                .addListenerForSingleValueEvent(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            String pass = dataSnapshot.child(teacherName).child("TeacherPass").getValue(String.class);

                            if (pass.equals(teacherPass)) {
                                Toasty.success(getContext(), "Login Successful", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getContext(), TeacherDashboardActivity.class));
                                getActivity().finish();
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                                        "loginStatus", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("login",true);
                                editor.apply();

                                Intent intent = new Intent(getContext(), TeacherDashboardActivity.class)
                                        ;
                                intent.putExtra("teachername", teacherName);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                Toasty.error(getContext(), "Password is wrong", Toast.LENGTH_LONG).show();
                            }


                        } else {
                            Toasty.error(getContext(), "Fail", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}