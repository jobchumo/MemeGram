package com.jobchumo.memegram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void Login(View view) {
        //finish();
        startActivity(new Intent(Welcome.this, Login.class));
    }

    public void Sign_up(View view) {
        //finish()
        startActivity(new Intent(Welcome.this, SignUp.class));
    }
}
