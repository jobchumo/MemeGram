package com.jobchumo.memegram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    protected TextView emailView, logout, usernameView;
    protected EditText fullname, confPass, password;
    protected Button updateBtn;
    protected ImageView logoutIcon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);


        if (!SharedPrefManager.getInstance(getContext()).isLoggedIn()) {
            getActivity().finish();
            startActivity(new Intent(getContext(), Login.class));
        }


        usernameView = profileView.findViewById(R.id.userNameView);
        emailView = profileView.findViewById(R.id.profilename);
        final User user = SharedPrefManager.getInstance(getContext()).getUser();
        emailView.setText(user.getEmail());
        usernameView.setText(user.getUsername());


        logout = profileView.findViewById(R.id.log_out);
        logoutIcon = profileView.findViewById(R.id.log_out_icon);
        fullname = profileView.findViewById(R.id.fullname);
        confPass = profileView.findViewById(R.id.confpass_word);
        password = profileView.findViewById(R.id.pass_word);
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
                String pass_word = password.getText().toString().trim();
                String confpassword = confPass.getText().toString().trim();

                if (TextUtils.isEmpty(full_name) || TextUtils.isEmpty(pass_word) || TextUtils.isEmpty(confpassword)) {
                    Toast.makeText(getContext(), "Empty fields!! \nPlease Enter all your details", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass_word.equals(confpassword)) {
                    updateProfile(full_name, pass_word);
                } else {
                    Toast.makeText(getContext(), "Passwords do not match!! \nPlease try again", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        return profileView;
    }

    private void updateProfile(String full_name, String pass_word) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Updating your profile..");
        progressDialog.show();

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        String username = user.getUsername();
        String url = "https://memes.mobisoko.co.ke/update/?auth=yeet";


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_name", username);
            jsonObject.put("full_name", full_name);
            jsonObject.put("pass_word", pass_word);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("UpdateResponse", response.toString());
                        Toast.makeText(getContext(), "Profile update Successful", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("UpdateError", error.toString());
                Toast.makeText(getContext(), "Profile update Failed", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }
}
