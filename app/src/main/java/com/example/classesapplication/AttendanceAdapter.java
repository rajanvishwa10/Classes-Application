package com.example.classesapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.classesapplication.Student.SuggestionsActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class AttendanceAdapter extends FirebaseRecyclerAdapter<Attendance, AttendanceAdapter.ViewHolder> {

    public AttendanceAdapter(@NonNull FirebaseRecyclerOptions<Attendance> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final AttendanceAdapter.ViewHolder holder, int i, @NonNull Attendance attendance) {
        String image = attendance.getProfileImage();
        Glide.with(holder.imageView.getContext()).load(image).into(holder.imageView);

        final String name = attendance.getName();
        holder.textView.setText(name);

        String studentclass = attendance.getcLass();
        holder.textView2.setText("Class : " + studentclass);


        Date c = Calendar.getInstance().getTime();
//System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy, h:mm aaa", Locale.getDefault());
        final String date = df.format(c);


        holder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textView3.setVisibility(View.VISIBLE);
                holder.textView3.setText("Present");
                holder.present.setVisibility(View.GONE);
                holder.absent.setVisibility(View.GONE);
                SharedPreferences sharedPreferences = holder.present.getContext().getSharedPreferences("teachername", Context.MODE_PRIVATE);
                String TeacherName = sharedPreferences.getString("teachername", "");
                addAttendance(holder.present.getContext(), "Present", TeacherName, name, date);
                Toast.makeText(holder.present.getContext(), "Present", Toast.LENGTH_SHORT).show();
            }
        });

        holder.absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textView3.setVisibility(View.VISIBLE);
                holder.textView3.setText("Absent");
                holder.present.setVisibility(View.GONE);
                holder.absent.setVisibility(View.GONE);
                SharedPreferences sharedPreferences = holder.absent.getContext().getSharedPreferences("teachername", Context.MODE_PRIVATE);
                String TeacherName = sharedPreferences.getString("teachername", "");
                addAttendance(holder.absent.getContext(), "Absent", TeacherName, name, date);
                Toast.makeText(holder.absent.getContext(), "Absent", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addAttendance(final Context context, String attendance, String TeacherName, String StudentName, String Date) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Attendance", attendance);
        hashMap.put("TeacherName", TeacherName);
        hashMap.put("StudentName", StudentName);
        hashMap.put("Date", Date);
        long count = System.currentTimeMillis();
        FirebaseDatabase.getInstance().getReference("Teachers").child("Student Attendance").child("attendance " + count)
                .updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toasty.success(context, "Attendance Added", Toast.LENGTH_SHORT).show();

                } else {
                    Toasty.error(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentattendance, parent, false);

        return new AttendanceAdapter.ViewHolder(view);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView2, textView3;
        ImageView imageView;
        ImageButton present, absent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.studentName);
            textView2 = itemView.findViewById(R.id.studentClass);
            textView3 = itemView.findViewById(R.id.presentText);
            imageView = itemView.findViewById(R.id.imageView);
            present = itemView.findViewById(R.id.present);
            absent = itemView.findViewById(R.id.absent);
        }
    }
}
