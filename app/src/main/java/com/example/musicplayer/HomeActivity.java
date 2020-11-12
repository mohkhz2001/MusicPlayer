package com.example.musicplayer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.musicplayer.Fragments.ListFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;

public class HomeActivity extends AppCompatActivity {

    BottomBar navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        InitViews();
        Start();
        ViewsController();


    }

    private void InitViews() {
        navigation = findViewById(R.id.bottomBar);

    }

    private void ViewsController() {
        navigation.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(int tabId) {
                if (tabId == R.id.tab_list) {
                    ListFragment listFragment = new ListFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, listFragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private void Start() {
        ListFragment listFragment = new ListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, listFragment);
        fragmentTransaction.commit();
    }
}