package com.jobchumo.memegram;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    protected EditText emaili, passwo;
    protected ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emaili = (EditText) findViewById(R.id.emailaddress);
        passwo = (EditText) findViewById(R.id.passw);
        progressBar = new ProgressBar(this);
    }

    public void Log_in(View view) {
        String email = emaili.getText().toString().trim();
        String pas = passwo.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pas)) {
            Toast.makeText(Login.this, "Empty fields!! \nPlease Enter all your details", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void Sign_up(View view) {
        startActivity(new Intent(Login.this, SignUp.class));
    }
}
