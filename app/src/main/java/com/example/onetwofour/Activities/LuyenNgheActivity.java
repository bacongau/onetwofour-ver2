package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.onetwofour.Adapter.TopicAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.TopicNguPhap;
import com.example.onetwofour.R;

import java.util.ArrayList;

public class LuyenNgheActivity extends AppCompatActivity {
    String DATABASE_NAME = "NguPhap db.db";
    SQLiteDatabase database;

    ListView lv;
    ArrayList<TopicNguPhap> arrayList;
    TopicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luyen_nghe);

        anhxa();
        docdulieu();
        
    }

    private void docdulieu() {
        database = DataBase.initDatabase(LuyenNgheActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Nguphap", null);
        arrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String tentopic = cursor.getString(0);
            byte[] hinh = cursor.getBlob(1);
            arrayList.add(new TopicNguPhap(hinh, tentopic));
        }
        adapter.notifyDataSetChanged();
    }

    private void anhxa() {
        lv = findViewById(R.id.lv_bainghe);
        arrayList = new ArrayList<>();
        adapter = new TopicAdapter(LuyenNgheActivity.this, R.layout.item_topic_luyennghe, arrayList);
        lv.setAdapter(adapter);
    }
}