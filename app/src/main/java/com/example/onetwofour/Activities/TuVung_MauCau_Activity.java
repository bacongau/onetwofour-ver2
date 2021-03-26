package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.onetwofour.Adapter.NguPhapViewPagerAdapter;
import com.example.onetwofour.Fragments.MauCau_Fragment;
import com.example.onetwofour.Fragments.TuVung_Fragment;
import com.example.onetwofour.R;
import com.google.android.material.tabs.TabLayout;

public class TuVung_MauCau_Activity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    NguPhapViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu_vung__mau_cau_);

        anhxa();

    }

    public String getMyData() {
        Bundle bundle = getIntent().getExtras();
        String a = (String) bundle.get("topic");
        return a;
    }

    private void anhxa() {
        tabLayout = findViewById(R.id.tablayout_nguphap);
        viewPager = findViewById(R.id.viewpager_nguphap);
        pagerAdapter = new NguPhapViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}