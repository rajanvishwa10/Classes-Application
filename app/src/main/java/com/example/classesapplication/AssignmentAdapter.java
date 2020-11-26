package com.example.classesapplication;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import es.dmoral.toasty.Toasty;

public class AssignmentAdapter extends FirebaseRecyclerAdapter<Assignments, AssignmentAdapter.ViewHolder> {


    public AssignmentAdapter(@NonNull FirebaseRecyclerOptions<Assignments> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final AssignmentAdapter.ViewHolder holder, int i, @NonNull Assignments assignments) {
        final String image = assignments.getAssignment_Image();
        Glide.with(holder.imageView.getContext()).load(image).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imageView.getContext(), ImageActivity.class);
                intent.putExtra("image",image);
                holder.imageView.getContext().startActivity(intent);
            }
        });

        final String details = assignments.getAssignment_Details();
        holder.Assignment_details.setText(details);

        String date = assignments.getDate();
        String splitDate[] = date.split("\\s+");
        holder.date.setText("Date : " + splitDate[0]);
        holder.time.setText("Time : " + splitDate[1]);
    }

    @NonNull
    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment, parent, false);

        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Assignment_details, date, time;
        ImageView imageView;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            Assignment_details = itemView.findViewById(R.id.details);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
