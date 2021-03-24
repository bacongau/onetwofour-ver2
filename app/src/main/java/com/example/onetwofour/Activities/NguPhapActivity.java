package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ListView;

import com.example.onetwofour.Adapter.TopicAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.TopicNguPhap;
import com.example.onetwofour.R;

import java.util.ArrayList;

public class NguPhapActivity extends AppCompatActivity {
    String DATABASE_NAME = "NguPhap db.db";
    SQLiteDatabase database;

    ArrayList<TopicNguPhap> arrayList;
    TopicAdapter adapter;
    ListView lv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngu_phap);
        
        anhxa();
        docdulieu();

    }

    private void docdulieu() {
        database = DataBase.initDatabase(NguPhapActivity.this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Nguphap", null);
        arrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String tentopic = cursor.getString(0);
            byte[] hinh = cursor.getBlob(1);
            arrayList.add(new TopicNguPhap(hinh,tentopic));
        }
        adapter.notifyDataSetChanged();
    }

    private void anhxa() {
        lv = findViewById(R.id.lv_topic_nguphap);
        arrayList = new ArrayList<>();
        adapter = new TopicAdapter(NguPhapActivity.this,R.layout.item_topic_nguphap,arrayList);
        lv.setAdapter(adapter);
    }
}