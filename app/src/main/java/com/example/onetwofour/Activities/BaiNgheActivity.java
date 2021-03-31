package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.BaiDoc;
import com.example.onetwofour.Model.BaiNghe;
import com.example.onetwofour.R;

import java.io.IOException;
import java.util.ArrayList;

public class BaiNgheActivity extends AppCompatActivity {
    String DATABASE_NAME = "LuyenNghe db.db";
    SQLiteDatabase database;

    ArrayList<BaiNghe> arrayList;
    ArrayList<BaiNghe> arrayList1;

    TextView tv_script, tv_tenbainghe, tv_current_time, tv_total_time, tv_bainghetiep;
    ImageView img_dish, img_previous, img_play_pause, img_stop, img_next;
    SeekBar audioProgess;
    ProgressBar progressBar;

    String tenbainghe;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_nghe);

        tenbainghe = getMyData();

        anhxa();
        laydulieu();
        laybainghehientai();

        img_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choinhac();
                setTenBaiNghe();
            }
        });

//        textView = findViewById(R.id.tv_script);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(BaiNgheActivity.this,BaiNgheScriptActivity.class));
//            }
//        });
    }

    private void setTenBaiNghe() {
        String c = arrayList1.get(0).getTen();
        tv_tenbainghe.setText(c);
    }

    private void choinhac() {
        String url = arrayList1.get(0).getLink();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            progressBar.setVisibility(View.VISIBLE);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.GONE);

                    if (mp.isPlaying()){
                        mp.pause();
                        img_play_pause.setImageResource(R.drawable.play);
                    }else {
                        mp.start();
                        img_play_pause.setImageResource(R.drawable.pause);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void laybainghehientai() {
        database = DataBase.initDatabase(BaiNgheActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Luyennghe WHERE ten = '" + tenbainghe + "'", null);
        arrayList1.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ten = cursor.getString(0);
            byte[] hinh = cursor.getBlob(1);
            String link = cursor.getString(2);
            String script = cursor.getString(3);
            arrayList1.add(new BaiNghe(ten, hinh, link, script));
        }
    }

    private void laydulieu() {
        database = DataBase.initDatabase(BaiNgheActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Luyennghe", null);
        arrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ten = cursor.getString(0);
            byte[] hinh = cursor.getBlob(1);
            String link = cursor.getString(2);
            String script = cursor.getString(3);
            arrayList.add(new BaiNghe(ten, hinh, link, script));
        }
    }

    private void anhxa() {
        tv_tenbainghe = findViewById(R.id.tv_audio_tenbainghe);
        tv_script = findViewById(R.id.tv_audio_script);
        tv_current_time = findViewById(R.id.tv_audio_current_time);
        tv_total_time = findViewById(R.id.tv_audio_total_time);
        tv_bainghetiep = findViewById(R.id.tv_audio_bainghe_tieptheo);
        img_dish = findViewById(R.id.img_audio_dish);
        img_play_pause = findViewById(R.id.img_audio_play_pause);
        img_previous = findViewById(R.id.img_audio_previous);
        img_stop = findViewById(R.id.img_audio_stop);
        img_next = findViewById(R.id.img_audio_next);
        audioProgess = findViewById(R.id.sb_audio_progess);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
    }

    public String getMyData() {
        Bundle bundle = getIntent().getExtras();
        String a = (String) bundle.get("tenbainghe");
        return a;
    }
}