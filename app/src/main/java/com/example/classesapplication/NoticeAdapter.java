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

public class NoticeAdapter extends FirebaseRecyclerAdapter<Notice, NoticeAdapter.ViewHolder> {

    public NoticeAdapter(@NonNull FirebaseRecyclerOptions<Notice> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final NoticeAdapter.ViewHolder holder, int i, @NonNull Notice notice) {
        final String image = notice.getNoticeImage();
        Glide.with(holder.imageView.getContext()).load(image).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.imageView.getContext(), ImageActivity.class);
                intent.putExtra("image",image);
                holder.imageView.getContext().startActivity(intent);
            }
        });
        final String name = notice.getNoticeText();
        holder.noticeTextView.setText(name);

        String date = notice.getDate();
        String splitDate[] = date.split("\\s+");
        holder.noticeDateTextView.setText("Date : "+splitDate[0]);
        holder.noticeTimeTextView.setText("Time : "+splitDate[1]);
    }


    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice, parent, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noticeTextView, noticeDateTextView, noticeTimeTextView;
        ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.noticeImageView);
            noticeTextView = view.findViewById(R.id.noticeTextView);
            noticeDateTextView = view.findViewById(R.id.noticeDateTextView);
            noticeTimeTextView = view.findViewById(R.id.noticeTimeTextView);
        }
    }
}
