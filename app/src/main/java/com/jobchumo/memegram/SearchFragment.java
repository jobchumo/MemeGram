package com.jobchumo.memegram;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View profileView = inflater.inflate(R.layout.fragment_search, container, false);

        ArrayList<MemePosts> memeposts = new ArrayList<>();
        memeposts.add(new MemePosts(R.drawable.karencat, "CJ701", "911 I'd like to report..."));
        memeposts.add(new MemePosts(R.drawable.nickfury, "CJKipu", "Now you see me, Now you don't"));

        recyclerView = profileView.findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new MemeAdapter(memeposts);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return profileView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //inflater.inflate(R.menu.search_menu, menu);

        MenuItem search_menu = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) search_menu.getActionView();
        super.onCreateOptionsMenu(menu, inflater);
    }
}
