package com.jobchumo.memegram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class Login extends AppCompatActivity {
    protected EditText user, passwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.user_Name);
        passwo = findViewById(R.id.passw);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(Login.this, MainActivity.class));
        }
    }

    public void Log_in(View view) {
        String userName = user.getText().toString().trim();
        String pas = passwo.getText().toString().trim();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pas)) {
            Toast.makeText(Login.this, "Empty fields!! \nPlease Enter all your details", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            loginRequest(userName, pas);
        }

    }

    private void loginRequest(String userName, String pas) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging you in...");
        progressDialog.show();
        String url = "https://postman-echo.com/post";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_name", userName);
            jsonObject.put("pass_word", pas);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        Log.d("LoginResponse", response.toString());
                        try {
                            JSONObject obj = response.getJSONObject(0);
                            User user = new User(
                                    obj.getString("user_name"),
                                    obj.getString("email_address"),
                                    obj.getString("pass_word")
                            );
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                Log.d("LoginError", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void Sign_up(View view) {
        startActivity(new Intent(Login.this, SignUp.class));
    }
}
