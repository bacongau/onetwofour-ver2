package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.onetwofour.R;

public class TroChoiActivity extends AppCompatActivity {
    ImageView img_quiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tro_choi);

        img_quiz= findViewById(R.id.img_quiz);
        img_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TroChoiActivity.this,GameQuizActivity.class));
            }
        });
    }
}