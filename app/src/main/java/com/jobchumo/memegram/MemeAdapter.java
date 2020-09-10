package com.jobchumo.memegram;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MemeAdapter extends RecyclerView.Adapter<MemeAdapter.MemeViewHolder> {

    protected ArrayList<MemePosts> mMemeList;
    public Context context;

    public MemeAdapter(ArrayList<MemePosts> mMemeList, Context context) {
        this.mMemeList = mMemeList;
        this.context = context;
    }

    public static class MemeViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mUsername;
        public TextView mCaption;
        public TextView mCategory;
        public ImageButton like, dislike, share;
        //public TextView mDateCreated;

        public MemeViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.postmeme);
            mUsername = itemView.findViewById(R.id.userpost);
            mCaption = itemView.findViewById(R.id.caption);
            share = itemView.findViewById(R.id.icon_share);
            mCategory = itemView.findViewById(R.id.category_Name);

        }
    }



    @NonNull
    @Override
    public MemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme_post, parent, false);
        MemeViewHolder mvh = new MemeViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MemeViewHolder holder, int position) {
        final MemePosts currentMeme = mMemeList.get(position);
        Picasso.get().load(currentMeme.getmImageResource()).into(holder.mImageView);
        holder.mCaption.setText(currentMeme.getmCaption());
        holder.mUsername.setText(currentMeme.getmUsername());
        holder.mCategory.setText(currentMeme.getmCategory());
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemePosts memePosts = new MemePosts();
                Intent shareIntent = new Intent(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, "Check out this meme: "+currentMeme.getmImageResource());
                context.startActivity(Intent.createChooser(shareIntent, "Share With"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMemeList.size();
    }
}

