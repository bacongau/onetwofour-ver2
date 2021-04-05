package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.onetwofour.Adapter.NguPhapAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.TopicNguPhap;
import com.example.onetwofour.R;

import java.util.ArrayList;

public class NguPhapActivity extends AppCompatActivity {
    String DATABASE_NAME = "NguPhap db.db";
    SQLiteDatabase database;

    ArrayList<TopicNguPhap> arrayList;
    NguPhapAdapter adapter, adapterSearch;
    RecyclerView rv;
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

                if (b.equalsIgnoreCase("")) {
                    rv.setAdapter(adapter);
                } else {
                    adapterSearch = new NguPhapAdapter( arrayListSearch);
                    rv.setAdapter(adapterSearch);
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

        rv = findViewById(R.id.rv_topic_nguphap);
        rv.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter = new NguPhapAdapter(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NguPhapActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager); // set kieu sap xep cac item trong recyclerview

        rv.setAdapter(adapter);
    }
}
