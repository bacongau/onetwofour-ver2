package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.onetwofour.R;

public class FeedbackActivity extends AppCompatActivity {
    RatingBar ratingBar;
    Button btn;
    EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        anhxa();
        ratingBar.setNumStars(5);
    }

    private void anhxa() {
        ratingBar = findViewById(R.id.ratingBar);
        btn = findViewById(R.id.btn_publish_feedback);
        edt = findViewById(R.id.edt_feedback);
    }
}