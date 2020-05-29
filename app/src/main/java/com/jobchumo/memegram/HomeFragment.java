package com.jobchumo.memegram;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<MemePosts> mMemePost;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<MemePosts> memeposts = new ArrayList<>();
        memeposts.add(new MemePosts(R.drawable.undercovercops, "CJKipu", "Undercover Cops and Taxpayer"));
        memeposts.add(new MemePosts(R.drawable.dnavsrna, "JChumo27", "DNA Vs RNA"));
        memeposts.add(new MemePosts(R.drawable.karencat, "CJ701", "911 I'd like to report..."));
        memeposts.add(new MemePosts(R.drawable.nickfury, "CJKipu", "Now you see me, Now you don't"));
        memeposts.add(new MemePosts(R.drawable.memevsanime, "JChumo27", "MVS"));
        memeposts.add(new MemePosts(R.drawable.pokemon, "CJKipu", "The eye sees only what the mind is prepared to comprehend"));
        memeposts.add(new MemePosts(R.drawable.skull, "CJKipu", "To see what is right in front of you're eyes,"));

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new MemeAdapter(memeposts);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
