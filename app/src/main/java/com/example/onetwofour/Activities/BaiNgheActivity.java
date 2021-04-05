package com.example.onetwofour.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BaiNgheActivity extends AppCompatActivity {
    DatabaseReference mDatabase;

    ArrayList<BaiNghe> arrayList;

    TextView tv_script, tv_tenbainghe, tv_current_time, tv_total_time, tv_bainghetiep;
    ImageView img_dish, img_previous, img_play_pause, img_stop, img_next;
    SeekBar seekBar;
    ProgressBar progressBar;

    String a;
    BaiNghe baiNghe;
    int position = 0;
    boolean loading = false;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_nghe);

        anhxa();
        laydulieu();


        img_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loading == true) {
                    phatAudio();
                    setTenBaiNghe(baiNghe);
                    UpdateTimeSong();
                }
            }
        });

        img_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loading == true) {
                    dungAudio();
                    setTenBaiNghe(baiNghe);
                }
            }
        });

        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loading == true) {
                    position++;
                    if (position > arrayList.size() - 1) {
                        position = 0;
                    }
                    baiNghe = arrayList.get(position);
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    khoiTaoMedia(baiNghe);
                    setTenBaiNghe(baiNghe);
                    mediaPlayer.start();
                    img_play_pause.setImageResource(R.drawable.play);
                    SetTimeTotal();
                    UpdateTimeSong();
                    if (position == arrayList.size() - 1) {
                        tv_bainghetiep.setText("Next: "+arrayList.get(0).getTen());
                    } else {
                        tv_bainghetiep.setText("Next: "+arrayList.get(position + 1).getTen());
                    }
                }
            }
        });

        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loading == true) {

                    position--;
                    if (position < 0) {
                        position = arrayList.size() - 1;
                    }
                    baiNghe = arrayList.get(position);
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    khoiTaoMedia(baiNghe);
                    setTenBaiNghe(baiNghe);
                    mediaPlayer.start();
                    img_play_pause.setImageResource(R.drawable.play);
                    SetTimeTotal();
                    UpdateTimeSong();
                    if (position == arrayList.size() - 1) {
                        tv_bainghetiep.setText("Next: "+arrayList.get(0).getTen());
                    } else {
                        tv_bainghetiep.setText("Next: "+arrayList.get(position + 1).getTen());
                    }
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        tv_script.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loading == true) {
                    String m = baiNghe.getTen();
                    Intent intent = new Intent(BaiNgheActivity.this, BaiNgheScriptActivity.class);
                    intent.putExtra("scriptBaiNghe", m);
                    startActivity(intent);
                }
            }
        });

    }

    private void khoitaocacGiaTri() {
        // lay bai nghe
        a = getMyData();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getTen().equals(a)) {
                baiNghe = arrayList.get(i);
                position = i;
            }
        }

        // doi ten bai nghe
        tv_tenbainghe.setText(baiNghe.getTen());
        if (position == arrayList.size() - 1) {
            tv_bainghetiep.setText("Next: "+arrayList.get(0).getTen());
        } else {
            tv_bainghetiep.setText("Next: "+arrayList.get(position + 1).getTen());
        }

        khoiTaoMedia(baiNghe);
    }

    private void UpdateTimeSong() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                tv_current_time.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    private void SetTimeTotal() {
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        tv_total_time.setText(dinhDangGio.format(mediaPlayer.getDuration()) + "");
        seekBar.setMax(mediaPlayer.getDuration());
    }

    private void setTenBaiNghe(BaiNghe baiNghe) {
        tv_tenbainghe.setText(baiNghe.getTen());
    }

    private void dungAudio() {
        mediaPlayer.stop();
        mediaPlayer.release();
        img_play_pause.setImageResource(R.drawable.play);
        khoiTaoMedia(baiNghe);
    }

    private void phatAudio() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            img_play_pause.setImageResource(R.drawable.play);
        } else {
            mediaPlayer.start();
            img_play_pause.setImageResource(R.drawable.pause);
        }
        SetTimeTotal();
    }

    private void khoiTaoMedia(BaiNghe baiNghe) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(baiNghe.getLink());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void laydulieu() {
        mDatabase.child("bai nghe 1").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BaiNghe baiNghe = snapshot.getValue(BaiNghe.class);
                arrayList.add(baiNghe);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(BaiNgheActivity.this, "Da tai xong du lieu", Toast.LENGTH_SHORT).show();
                loading = true;
                khoitaocacGiaTri();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        seekBar = findViewById(R.id.sb_audio_progess);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        arrayList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public String getMyData() {
        Bundle bundle = getIntent().getExtras();
        String a = (String) bundle.get("tenbainghe");
        return a;
    }


    @Override
    protected void onDestroy() {
        dungAudio();
        super.onDestroy();
    }
}