package com.jobchumo.memegram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    protected EditText emaili, passwo;
    protected RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emaili = (EditText) findViewById(R.id.emailaddress);
        passwo = (EditText) findViewById(R.id.passw);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void Log_in(View view) {
        String email = emaili.getText().toString().trim();
        String pas = passwo.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pas)) {
            Toast.makeText(Login.this, "Empty fields!! \nPlease Enter all your details", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            loginRequest(email, pas);
        }

    }

    private void loginRequest(String email, String pas) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging you in...");
        progressDialog.show();
        String url = "https://memes.mobisoko.co.ke/users/test.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0;i<response.length();i++) {
                                JSONObject users = response.getJSONObject(i);

                            }
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void Sign_up(View view) {
        startActivity(new Intent(Login.this, SignUp.class));
    }
}
