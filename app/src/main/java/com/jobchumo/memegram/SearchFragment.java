package com.jobchumo.memegram;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private EditText searchQuery;
    private ImageButton searchButton;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<MemePosts> memeposts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View searchView = inflater.inflate(R.layout.fragment_search, container, false);

        memeposts = new ArrayList<>();
        recyclerView = searchView.findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new MemeAdapter(memeposts);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        searchQuery = searchView.findViewById(R.id.searchText);
        searchButton = searchView.findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(this);

        return searchView;
    }

    private void searchFunction() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Searching for your meme...");
        progressDialog.show();

        String s = searchQuery.getText().toString().trim();
        String url = "https://memes.mobisoko.co.ke/memes/?q="+s+"&auth=yeet";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("searchterm", s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("SearchResponse", response.toString());
                        try {
                            for (int i = 0 ; i<response.length(); i++){
                                JSONObject memes = response.getJSONObject(i);
                                MemePosts memePosts = new MemePosts();
                                memePosts.setmImageResource(memes.getString("Pic_Vid"));
                                memePosts.setmCaption(memes.getString("Caption"));
                                memePosts.setmUsername(memes.getString("Meme_Id"));
                                memeposts.add(memePosts);
                            }}catch (JSONException e) {
                            Log.d("SearchException", e.getMessage());
                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("SearchError", error.getMessage());

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchBtn:
                searchFunction();
                break;
        }
    }
}
