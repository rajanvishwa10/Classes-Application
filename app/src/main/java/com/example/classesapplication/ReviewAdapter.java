package com.example.classesapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

class ReviewAdapter extends FirebaseRecyclerAdapter<Review, ReviewAdapter.ViewHolder> {

    public ReviewAdapter(@NonNull FirebaseRecyclerOptions<Review> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder viewHolder, int i, @NonNull Review review) {

        viewHolder.textView.setText(review.getSuggestions());
        viewHolder.textView2.setText("Rating : "+review.getRating());
        viewHolder.textView5.setText("Teacher Name : "+review.getTeacherName());

        String date = review.getDate();


//        viewHolder.textView3.setText("Date : "+date);
        String splitDate[] = date.split("\\s+");
        viewHolder.textView3.setText("Date : "+splitDate[0]);
        viewHolder.textView4.setText("Time : "+splitDate[1]);
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_review, parent, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView2, textView3, textView4, textView5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.suggestions);
            textView2 = itemView.findViewById(R.id.review);
            textView3 = itemView.findViewById(R.id.date);
            textView4 = itemView.findViewById(R.id.time);
            textView5 = itemView.findViewById(R.id.teacherName);
        }
    }
}
