package com.example.onetwofour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.onetwofour.Activities.FeedbackActivity;
import com.example.onetwofour.Activities.LuyenDocActivity;
import com.example.onetwofour.Activities.LuyenNgheActivity;
import com.example.onetwofour.Activities.NguPhapActivity;
import com.example.onetwofour.Activities.TroChoiActivity;

public class MainActivity extends AppCompatActivity {
    LinearLayout lyt_nguphap,lyt_doc,lyt_nghe,lyt_choi;
    ImageView img_feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhxa();
        sukien();

    }

    private void sukien() {
        lyt_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LuyenDocActivity.class));
            }
        });

        lyt_choi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TroChoiActivity.class));
            }
        });

        lyt_nguphap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NguPhapActivity.class));
            }
        });

        lyt_nghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LuyenNgheActivity.class));
            }
        });

        img_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
            }
        });
    }

    private void anhxa() {
        lyt_nguphap = findViewById(R.id.lyt_nguphap);
        lyt_choi = findViewById(R.id.lyt_trochoi);
        lyt_doc = findViewById(R.id.lyt_luyendoc);
        lyt_nghe = findViewById(R.id.lyt_luyenghe);
        img_feedback = findViewById(R.id.img_feedback);
    }
}