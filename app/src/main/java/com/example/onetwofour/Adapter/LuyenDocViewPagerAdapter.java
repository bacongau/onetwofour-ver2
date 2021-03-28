package com.example.onetwofour.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.onetwofour.Fragments.EngSubFragment;
import com.example.onetwofour.Fragments.VietSubFragment;

public class LuyenDocViewPagerAdapter extends FragmentStatePagerAdapter {
    public LuyenDocViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new EngSubFragment();
            }
            case 1:{
                return new VietSubFragment();
            }
            default:{
                return new EngSubFragment();
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
                title = "Tieng Anh";
                break;
            }
            case 1:{
                title = "Tieng Viet";
                break;
            }
        }
        return title;
    }

}
