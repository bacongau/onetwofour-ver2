package com.example.onetwofour.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.onetwofour.Fragments.MauCau_Fragment;
import com.example.onetwofour.Fragments.TuVung_Fragment;

public class NguPhapViewPagerAdapter extends FragmentStatePagerAdapter {
    public NguPhapViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new TuVung_Fragment();
            }
            case 1:{
                return new MauCau_Fragment();
            }
            default:{
                return new TuVung_Fragment();
            }
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:{
                title = "Tu vung";
                break;
            }
            case 1:{
                title = "Mau cau";
                break;
            }
        }
        return title;
    }
}
