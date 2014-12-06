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
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FightCardsFragment.newInstance(FightCardsFragment.FightCardsType.FEATURED);
        } else {
            return FightCardsFragment.newInstance(FightCardsFragment.FightCardsType.PAST);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Featured";
        } else {
            return "Past";
        }
    }
}
