package com.jobchumo.memegram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SignUp extends AppCompatActivity {
    protected EditText emailadd, pass, confpass, usernam;
    protected ProgressBar progressBar;
    protected RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernam = (EditText) findViewById(R.id.userName);
        emailadd = (EditText) findViewById(R.id.email_address);
        pass = (EditText) findViewById(R.id.password);
        confpass = (EditText) findViewById(R.id.confirmpass);
        progressBar = new ProgressBar(this);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(SignUp.this, HomeFragment.class));
            return;
        }

    }

    public void signup(View view) {

        String username = usernam.getText().toString().trim();
        String emaill = emailadd.getText().toString().trim();
        String passwo = pass.getText().toString().trim();
        String confirm = confpass.getText().toString().trim();


        if (TextUtils.isEmpty(emaill) || TextUtils.isEmpty(passwo) || TextUtils.isEmpty(confirm) || TextUtils.isEmpty(username)) {
            Toast.makeText(SignUp.this, "Empty fields!! \nPlease Enter all your details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwo.equals(confirm)) {
            saveDate(emaill, passwo, username);
        } else {
            Toast.makeText(SignUp.this, "Passwords do not match!! \nPlease try again", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void saveDate(final String emaill, String passwo, String username) {

        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Registering User...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String url = "https://memes.mobisoko.co.ke/users/test.php";

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_name", username );
            jsonObject.put("email_address", emaill);
            jsonObject.put("pass_word", passwo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        User user = new User(username, emaill, passwo);
        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        Log.d("ResponseJS", response.toString());
                        Toast.makeText(SignUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this, Login.class));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("ErrorJS", error.toString());
                Toast.makeText(SignUp.this, "Error!! Registration Failed", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

}
