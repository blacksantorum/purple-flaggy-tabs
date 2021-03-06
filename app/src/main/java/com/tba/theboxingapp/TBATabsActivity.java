package com.tba.theboxingapp;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;


public class TBATabsActivity extends FragmentActivity {

    TBAFragmentPagerAdapter mFragmentPagerAdapter;
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbatabs);

        mFragmentPagerAdapter = new TBAFragmentPagerAdapter(getSupportFragmentManager());

        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.tba_background));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.tba_red));
        // mSlidingTabLayout.setDividerColors(Color.parseColor("#FFFFFF"));

        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(mFragmentPagerAdapter);

        mSlidingTabLayout.setViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tbatabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
