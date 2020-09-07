package com.jobchumo.memegram;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    protected TextView emailView;
    protected EditText fullname, username, password;
    protected Button updateBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);

        emailView = profileView.findViewById(R.id.profilename);
        final User user = SharedPrefManager.getInstance(getContext()).getUser();
        emailView.setText(user.getEmail());

        fullname = profileView.findViewById(R.id.fullname);
        username = profileView.findViewById(R.id.user_name);
        password = profileView.findViewById(R.id.passw);
        updateBtn = profileView.findViewById(R.id.update_button);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full_name = fullname.getText().toString().trim();
                String user_name = username.getText().toString().trim();
                String pass_word = password.getText().toString().trim();

                updateProfile(full_name, user_name, pass_word);
            }
        });

        return profileView;
    }

    private void updateProfile(String full_name, String user_name, String pass_word) {
        //args
    }
}
