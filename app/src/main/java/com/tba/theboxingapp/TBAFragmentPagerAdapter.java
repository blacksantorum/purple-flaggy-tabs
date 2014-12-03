package com.tba.theboxingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by christibbs on 12/2/14.
 */
public class TBAFragmentPagerAdapter extends FragmentPagerAdapter
{
    public TBAFragmentPagerAdapter (FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        return new Fragment();
    }
}
