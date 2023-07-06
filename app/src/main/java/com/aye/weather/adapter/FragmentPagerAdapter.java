package com.aye.weather.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList;
    public FragmentPagerAdapter(FragmentManager fragmentManager,List<Fragment> fragments) {
        super(fragmentManager);
        this.fragmentList=fragments;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
