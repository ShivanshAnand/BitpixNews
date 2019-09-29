package com.bitpix.bitpixnews.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bitpix.bitpixnews.R;
import com.bitpix.bitpixnews.getnews.StructN18;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HeadlinesListAdapter extends RecyclerView.Adapter<HeadlinesListAdapter.HeadlinesVH> {

    public ArrayList<StructN18> headlines;
    public Context context;
    public OnItemClickListener onItemClickListener;

    public HeadlinesListAdapter(Context context, ArrayList<StructN18> headlines, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.headlines = headlines;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HeadlinesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new HeadlinesVH(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadlinesVH holder, int position) {
        StructN18 item = headlines.get(position);
        holder.title.setText(item.getTitle());
        holder.pubTime.setText(item.getPubTime());
        Glide.with(context).load(item.getImgUrl()).placeholder(R.mipmap.ic_launcher).centerCrop().into(holder.img);
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }

    public class HeadlinesVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView pubTime;
        private ImageView img;
        OnItemClickListener onItemClickListener;
        public HeadlinesVH(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.ni_title);
            pubTime = itemView.findViewById(R.id.ni_pubtime);
            img = itemView.findViewById(R.id.ni_image);
            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}
