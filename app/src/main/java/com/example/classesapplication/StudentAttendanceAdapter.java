package com.example.classesapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class StudentAttendanceAdapter extends FirebaseRecyclerAdapter<Attendance2, StudentAttendanceAdapter.ViewHolder> {

    public StudentAttendanceAdapter(@NonNull FirebaseRecyclerOptions<Attendance2> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull StudentAttendanceAdapter.ViewHolder viewHolder, int i, @NonNull Attendance2 attendance2) {
        String teacherName = attendance2.getTeacherName();
        String studentName = attendance2.getStudentName();
        String date = attendance2.getDate();
        String attendance = attendance2.getAttendance();

        if(attendance.equals("Present")){
            viewHolder.textView3.setTextColor(Color.parseColor("#05AA00"));
        }else if(attendance.equals("Absent")){
            viewHolder.textView3.setTextColor(Color.parseColor("#EA0000"));
        }

        SharedPreferences sharedPreferences = viewHolder.textView.getContext().getSharedPreferences("Student", Context.MODE_PRIVATE);
        String student = sharedPreferences.getString("name","");
        if(studentName.equals(student)){
            viewHolder.textView.setText("Teacher Name : "+teacherName);
            viewHolder.textView2.setText(date);

            viewHolder.textView3.setText(attendance);
        }else {
            viewHolder.cardView.setVisibility(View.GONE);
        }

    }

    @NonNull
    @Override
    public StudentAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_attendance, parent, false);

        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView2, textView3;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.teacherName);
            textView2 = itemView.findViewById(R.id.date);
            textView3 = itemView.findViewById(R.id.attendance);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
