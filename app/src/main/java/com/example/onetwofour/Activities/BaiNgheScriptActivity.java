package com.example.onetwofour.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.BaiNghe;
import com.example.onetwofour.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BaiNgheScriptActivity extends AppCompatActivity {
    DatabaseReference mDatabase;

    ArrayList<BaiNghe> arrayList;
    BaiNghe baiNghe;
    TextView tv;
    String b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_nghe_script);

        tv = findViewById(R.id.tv_script_bainghe);
        arrayList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        laydulieu();
        b = getMyData();

    }

    public String getMyData() {
        Bundle bundle = getIntent().getExtras();
        String a = (String) bundle.get("scriptBaiNghe");
        return a;
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
                for (int i = 0; i < arrayList.size(); i++) {
                    if (b.equals(arrayList.get(i).getTen())) {
                        baiNghe = arrayList.get(i);
                    }
                }

                tv.setText(baiNghe.getScript());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}