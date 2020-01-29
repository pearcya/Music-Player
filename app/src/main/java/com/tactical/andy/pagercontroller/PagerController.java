package com.tactical.andy.pagercontroller;

import com.tactical.andy.allmusic.AllMusic;
import com.tactical.andy.playlist.Playlist;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerController extends FragmentPagerAdapter {

    int tabCounts;

    public PagerController(FragmentManager fm, int tabcounts) {
        super(fm);

        this.tabCounts = tabcounts;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new AllMusic();
            case 1:
                return new Playlist();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCounts;
    }
}
