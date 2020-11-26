package com.example.classesapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class TimetableAdapter extends FirebaseRecyclerAdapter<TimeTable, TimetableAdapter.ViewHolder> {

    public TimetableAdapter(@NonNull FirebaseRecyclerOptions<TimeTable> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final TimetableAdapter.ViewHolder holder, int i, @NonNull TimeTable timeTable) {
        final String image = timeTable.getTimetableImage();
        Glide.with(holder.imageView.getContext()).load(image).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imageView.getContext(), ImageActivity.class);
                intent.putExtra("image",image);
                holder.imageView.getContext().startActivity(intent);
            }
        });

        final String name = timeTable.getTeacherName();
        holder.teacherTextview.setText("Teacher Name : " + name);

        String date = timeTable.getDate();
        String splitDate[] = date.split("\\s+");
        holder.dateTextview.setText("Date : "+splitDate[0]);
        holder.timeTextview.setText("Time : "+splitDate[1]);
    }

    @NonNull
    @Override
    public TimetableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_timetable, parent, false);

        return new TimetableAdapter.ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextview, timeTextview, teacherTextview;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.timetableImageView);
            dateTextview = itemView.findViewById(R.id.timetableDateTextView);
            timeTextview = itemView.findViewById(R.id.timetableTimeTextView);
            teacherTextview = itemView.findViewById(R.id.timetableTextView);
        }
    }
}
