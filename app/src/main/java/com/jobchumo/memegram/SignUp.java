package com.jobchumo.memegram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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

        }
        else {
            Toast.makeText(SignUp.this, "Passwords do not match!! \nPlease try again", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}
