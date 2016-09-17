package com.example.yijinkang.pantryapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Admin on 11-12-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new ListRecipesFragment();    // Which Fragment should be dislpayed by the viewpager for the given position
        }
        // In my case we are showing up only one fragment in all the three tabs so we are
        // not worrying about the position and just returning the ListRecipesFragment
        return new ListRecipesFragment();
    }

    @Override
    public int getCount() {
        return 3;           // As there are only 3 Tabs
    }

}
