package com.jobchumo.memegram;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    String url = "https://memes.mobisoko.co.ke/memes/";
    private ArrayList<MemePosts> mMemePost;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mMemePost = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new MemeAdapter(mMemePost);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        getData();

        return rootView;
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Humor Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
              try {
                  for (int i = 0 ; i<response.length(); i++){
                      JSONObject memes = response.getJSONObject(i);
                      MemePosts memePosts = new MemePosts();
                      memePosts.setmImageResource(memes.getString("Pic_Vid"));
                      memePosts.setmCaption(memes.getString("Caption"));
                      memePosts.setmUsername(memes.getString("Meme_Id"));
                      mMemePost.add(memePosts);
                  }
              }
              catch (JSONException e){
                  Toast.makeText(getContext(), "JSON is not valid", Toast.LENGTH_SHORT).show();
              }
              adapter.notifyDataSetChanged();
              progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

}
