package com.jobchumo.memegram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Log_in(View view) {
    }

    public void Sign_up(View view) {
        startActivity(new Intent(Login.this, SignUp.class));
    }
}
