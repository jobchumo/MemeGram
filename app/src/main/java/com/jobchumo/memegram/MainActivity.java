package com.jobchumo.memegram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        ArrayList<ItemPosts> memeposts = new ArrayList<>();
        memeposts.add(new ItemPosts(R.drawable.nhajicpn26c21, "CJKIPU", "TCYL TLYS"));
        memeposts.add(new ItemPosts(R.drawable.pokemon, "JCHUMO27", "NYSM"));
        memeposts.add(new ItemPosts(R.drawable.nhajicpn26c21, "CJ2701", "JDI"));

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFrag = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFrag = new HomeFragment();
                            break;

                        case R.id.nav_upload:
                            selectedFrag = new UploadFragment();
                            break;

                        case R.id.nav_profile:
                            selectedFrag = new ProfileFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, selectedFrag).commit();

                    return true;
                }
            };
}
