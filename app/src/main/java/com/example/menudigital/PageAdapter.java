package com.example.menudigital;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PlatillosFragment();
            case 1:
                return new BebidasFragment();
            case 2:
                return new SnacksFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}

