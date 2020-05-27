package com.jobchumo.memegram;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.MemeViewHolder> {

    protected ArrayList<MemePosts> mMemeList;

    public static class MemeViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mUsername;
        public TextView mCaption;

        public MemeViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.postmeme);
            mUsername = itemView.findViewById(R.id.userpost);
            mCaption = itemView.findViewById(R.id.caption);
        }
    }

    public MemeAdapter(ArrayList<MemePosts> memeList){
        mMemeList = memeList;
    }

    @NonNull
    @Override
    public MemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme_post, parent, false);
        MemeViewHolder mvh = new MemeViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MemeViewHolder holder, int position) {
        MemePosts currentMeme = mMemeList.get(position);
        holder.mImageView.setImageResource(currentMeme.getmImageResource());
        holder.mCaption.setText(currentMeme.getmCaption());
        holder.mUsername.setText(currentMeme.getmUsername());
    }

    @Override
    public int getItemCount() {
        return mMemeList.size();
    }
}
