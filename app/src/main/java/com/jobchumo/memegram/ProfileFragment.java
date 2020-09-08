package com.jobchumo.memegram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    protected TextView emailView, logout;
    protected EditText fullname, username, password;
    protected Button updateBtn;
    protected ImageView logoutIcon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);


        if (!SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
            getActivity().finish();
            startActivity(new Intent(getContext(), Login.class));
        }


        emailView = profileView.findViewById(R.id.profilename);
        final User user = SharedPrefManager.getInstance(getContext()).getUser();
        emailView.setText(user.getEmail());

        logout = profileView.findViewById(R.id.log_out);
        logoutIcon = profileView.findViewById(R.id.log_out_icon);
        fullname = profileView.findViewById(R.id.fullname);
        username = profileView.findViewById(R.id.user_name);
        password = profileView.findViewById(R.id.passw);
        updateBtn = profileView.findViewById(R.id.update_button);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                SharedPrefManager.getInstance(getContext()).logout();
            }
        });

        logoutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                SharedPrefManager.getInstance(getContext()).logout();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full_name = fullname.getText().toString().trim();
                String user_name = username.getText().toString().trim();
                String pass_word = password.getText().toString().trim();

                updateProfile(full_name, user_name, pass_word);
            }
        });

        return profileView;
    }

    private void updateProfile(String full_name, String user_name, String pass_word) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Updating your profile..");
        progressDialog.show();

        String url = "";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("", "");
            jsonObject.put("", "");
            jsonObject.put("", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }
}
