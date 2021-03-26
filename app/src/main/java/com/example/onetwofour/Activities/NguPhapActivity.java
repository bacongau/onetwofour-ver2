package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
    TopicAdapter adapter, adapterSearch;
    ListView lv;
    Button btn_search;
    EditText edt_chude;
    ArrayList<TopicNguPhap> arrayListSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngu_phap);

        anhxa();
        docdulieu();



        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = edt_chude.getText().toString();
                arrayListSearch.clear();
                for (int i = 0; i < arrayList.size(); i++) {
                    if (b.equalsIgnoreCase(arrayList.get(i).getTopicName())) {
                        arrayListSearch.add(arrayList.get(i));
                    }
                }

                if (b.equalsIgnoreCase("All")) {
                    lv.setAdapter(adapter);
                } else {
                    adapterSearch = new TopicAdapter(NguPhapActivity.this, R.layout.item_topic_nguphap, arrayListSearch);
                    lv.setAdapter(adapterSearch);
                }


            }
        });
    }


    private void docdulieu() {
        database = DataBase.initDatabase(NguPhapActivity.this, DATABASE_NAME);
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
        btn_search = findViewById(R.id.btn_nguphap_find_topic);
        edt_chude = (EditText) NguPhapActivity.this.findViewById(R.id.edt_nguphap_find_topic);
        arrayListSearch = new ArrayList<>();

        lv = findViewById(R.id.lv_topic_nguphap);
        arrayList = new ArrayList<>();
        adapter = new TopicAdapter(NguPhapActivity.this, R.layout.item_topic_nguphap, arrayList);
        lv.setAdapter(adapter);
    }
}
