package com.example.classesapplication;

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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import es.dmoral.toasty.Toasty;

public class AttendanceAdapter extends FirebaseRecyclerAdapter<Attendance, AttendanceAdapter.ViewHolder> {

    public AttendanceAdapter(@NonNull FirebaseRecyclerOptions<Attendance> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final AttendanceAdapter.ViewHolder holder, int i, @NonNull Attendance attendance) {
        String image = attendance.getProfileImage();
        Glide.with(holder.imageView.getContext()).load(image).into(holder.imageView);

        String name = attendance.getName();
        holder.textView.setText(name);

        String studentclass = attendance.getcLass();
        holder.textView2.setText("Class : "+studentclass);

        holder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textView3.setVisibility(View.VISIBLE);
                holder.textView3.setText("Present");
                holder.present.setVisibility(View.GONE);
                holder.absent.setVisibility(View.GONE);
                Toast.makeText(holder.present.getContext(),"Present", Toast.LENGTH_SHORT).show();
            }
        });

        holder.absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textView3.setVisibility(View.VISIBLE);
                holder.textView3.setText("Absent");
                holder.present.setVisibility(View.GONE);
                holder.absent.setVisibility(View.GONE);
                Toast.makeText(holder.present.getContext(),"Absent", Toast.LENGTH_SHORT).show();
            }
        });

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
