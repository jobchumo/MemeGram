package com.jobchumo.memegram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignUp extends AppCompatActivity {
    protected EditText emailadd, pass, confpass;
    protected ProgressBar progressBar;
    protected RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailadd = (EditText) findViewById(R.id.email_address);
        pass = (EditText) findViewById(R.id.password);
        confpass = (EditText) findViewById(R.id.confirmpass);
        progressBar = new ProgressBar(this);

    }

    public void signup(View view) {

        String emaill = emailadd.getText().toString().trim();
        String passwo = pass.getText().toString().trim();
        String confirm = confpass.getText().toString().trim();


        if (TextUtils.isEmpty(emaill) || TextUtils.isEmpty(passwo) || TextUtils.isEmpty(confirm)) {
            Toast.makeText(SignUp.this, "Empty fields!! \nPlease Enter all your details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwo.equals(confirm)) {
            saveDate(emaill, passwo);
        } else {
            Toast.makeText(SignUp.this, "Passwords do not match!! \nPlease try again", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void saveDate(final String emaill, String passwo) {

        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Registering User...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String url = "https://memes.mobisoko.co.ke/users/";

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailaddress", emaill);
            jsonObject.put("password", passwo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this, "Success"+response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SignUp.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }


    public void post(View view) {
        MyVolleyRequest.getInstance(SignUp.this, new IVolley() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SignUp.this, "" + response, Toast.LENGTH_LONG).show();
            }
        }).postRequest("https://memes.mobisoko.co.ke/users/");
    }
}
