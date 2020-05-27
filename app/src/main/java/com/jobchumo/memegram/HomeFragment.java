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

    protected RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;
    protected RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<MemePosts> memeposts = new ArrayList<>();
        memeposts.add(new MemePosts(R.drawable.nhajicpn26c21, "CJKIPU", "TCYL TLYS"));
        memeposts.add(new MemePosts(R.drawable.pokemon, "JCHUMO27", "NYSM"));
        memeposts.add(new MemePosts(R.drawable.nhajicpn26c21, "CJ2701", "JDI"));

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new MemeAdapter(memeposts);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
