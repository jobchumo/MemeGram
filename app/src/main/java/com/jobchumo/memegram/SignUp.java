package com.jobchumo.memegram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class SignUp extends AppCompatActivity {
    protected EditText emailadd, pass, confpass;
    protected ProgressBar progressBar;
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

        if (TextUtils.isEmpty(emaill) || TextUtils.isEmpty(passwo) || TextUtils.isEmpty(confirm)){
            Toast.makeText(SignUp.this, "Empty fields!! \nPlease Enter all your details", Toast.LENGTH_SHORT).show();
            return;
            }
        if (passwo.equals(confirm)){
            saveDate(emaill, passwo);
        }
        else {
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

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Email_Address", emaill);
            jsonObject.put("Password", passwo);
        } catch (JSONException e) {
            //Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            String errorr = response.getString("htttpsStatuss");

                            if (errorr.equals("") || errorr.equals(null)) {

                            }
                            else  if (errorr.equals("OK")) {
                                JSONObject object = response.getJSONObject("object");

                            }
                            else {}
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SignUp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
        requestQueue.add(jsonObjectRequest);
    }


}
