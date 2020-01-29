package com.tactical.andy.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentController;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.tactical.andy.pagercontroller.PagerController;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TabLayout mTablayout;
    TabItem curMusic;
    TabItem allMusic;
    TabItem playlist;
    ViewPager mPager;
    PagerController mPagerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Music Player");


        mTablayout = findViewById(R.id.tabLayout);
        curMusic = findViewById(R.id.currentMusic);

        playlist = findViewById(R.id.playlist);
        mPager = findViewById(R.id.viewpager);

        mPagerController = new PagerController(getSupportFragmentManager(),mTablayout.getTabCount());
        mPager.setAdapter(mPagerController);



        //which tab is selected
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));


    }

}
