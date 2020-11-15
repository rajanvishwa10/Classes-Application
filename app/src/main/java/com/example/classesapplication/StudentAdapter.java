package com.example.classesapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.classesapplication.Student.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

class StudentAdapter extends FirebaseRecyclerAdapter<StudentGetClass, StudentAdapter.ViewHolder> {


    public StudentAdapter(FirebaseRecyclerOptions options) {
        super(options);
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist, parent, false);

        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int i, @NonNull final StudentGetClass studentGetClass) {

        final String image = studentGetClass.getProfileImage();
        Glide.with(holder.imageView.getContext()).load(image).into(holder.imageView);

        final String name = studentGetClass.getName();
        holder.textView.setText(name);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(holder.cardView.getContext(), name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(holder.cardView.getContext(), ViewstudentProfileActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("email", studentGetClass.getEmail());
                intent.putExtra("class", studentGetClass.getcLass());
                intent.putExtra("phone", studentGetClass.getMobile());
                intent.putExtra("profilepic", image);
                intent.putExtra("guardian", studentGetClass.getGuardianName());
                holder.cardView.getContext().startActivity(intent);
            }
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.studentName);
            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
