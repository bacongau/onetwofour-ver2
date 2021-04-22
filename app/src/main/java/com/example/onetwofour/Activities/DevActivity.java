package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.onetwofour.R;

public class DevActivity extends AppCompatActivity {
    Button btn_themchude,btn_themtuvung,btn_themmaucau,btn_thembaidoc,btn_thembainghe,btn_themquiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);

        anhxa();
        btn_themchude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DevActivity.this,ThemChuDeActivity.class));
            }
        });
        btn_themtuvung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DevActivity.this,ThemTuVungActivity.class));
            }
        });
        btn_themmaucau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DevActivity.this,ThemMauCauActivity.class));
            }
        });
        btn_thembainghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DevActivity.this,ThemBaiNgheActivity.class));
            }
        });
        btn_thembaidoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DevActivity.this,ThemBaiDocActivity.class));
            }
        });

        btn_themquiz.setVisibility(View.INVISIBLE);
    }

    private void anhxa() {
        btn_themchude = findViewById(R.id.btn_themchude);
        btn_themtuvung = findViewById(R.id.btn_themtuvung);
        btn_themmaucau = findViewById(R.id.btn_themmaucau);
        btn_thembaidoc = findViewById(R.id.btn_thembaidoc);
        btn_thembainghe = findViewById(R.id.btn_thembainghe);
        btn_themquiz = findViewById(R.id.btn_themquiz);
    }
}