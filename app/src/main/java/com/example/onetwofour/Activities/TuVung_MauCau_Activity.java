package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.onetwofour.R;

public class TuVung_MauCau_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu_vung__mau_cau_);

        Bundle bundle = getIntent().getExtras();
        String a = (String) bundle.get("topic");
    }
}