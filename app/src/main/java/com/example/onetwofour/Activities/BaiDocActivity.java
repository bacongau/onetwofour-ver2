package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.onetwofour.Adapter.LuyenDocViewPagerAdapter;
import com.example.onetwofour.Adapter.NguPhapViewPagerAdapter;
import com.example.onetwofour.R;
import com.google.android.material.tabs.TabLayout;

public class BaiDocActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    LuyenDocViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_doc);

        anhxa();

    }

    private void anhxa() {
        tabLayout = findViewById(R.id.tablayout_luyendoc);
        viewPager = findViewById(R.id.viewpager_luyendoc);
        pagerAdapter = new LuyenDocViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}