package com.jobchumo.memegram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(Welcome.this, MainActivity.class));
        }

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
