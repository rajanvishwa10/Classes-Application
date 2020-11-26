package com.example.classesapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        SharedPreferences sharedPreferences = holder.textView2.getContext().getSharedPreferences("teachername", Context.MODE_PRIVATE);
        final String TeacherName = sharedPreferences.getString("teachername", "");
        System.out.println(TeacherName);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        final String date = df.format(c);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Student Attendance");
        myRef.orderByChild("TeacherName").equalTo(TeacherName).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    myRef.orderByChild("StudentName").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                myRef.orderByChild("Date").equalTo(date).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        holder.present.setVisibility(View.GONE);
                                        holder.absent.setVisibility(View.GONE);
                                        holder.textView3.setVisibility(View.VISIBLE);
                                        SharedPreferences sharedPreferences = holder.absent.getContext().getSharedPreferences("teachername", Context.MODE_PRIVATE);
                                        String TeacherName = sharedPreferences.getString("teachername", "");
                                        String text = snapshot.child("attendance " + name + " " + TeacherName + " " + date).child("Attendance").getValue(String.class);

                                         if(text == null){
                                            holder.present.setVisibility(View.VISIBLE);
                                            holder.absent.setVisibility(View.VISIBLE);
                                            holder.textView3.setVisibility(View.GONE);
                                        }else{
                                             holder.textView3.setText(text);
                                         }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
//                                Toast.makeText(holder.textView.getContext(), "Data present", Toast.LENGTH_SHORT).show();

                            } else {
                                //Toast.makeText(holder.textView.getContext(), "Data absent", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    Toast.makeText(holder.textView.getContext(), "Data absent", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        FirebaseDatabase.getInstance().getReference().child("Student Attendance").child("attendance " + StudentName + " " + TeacherName+ " " + Date)
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
