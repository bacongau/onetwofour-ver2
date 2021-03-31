package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.onetwofour.Adapter.LuyenNgheAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.BaiNghe;
import com.example.onetwofour.R;

import java.util.ArrayList;

public class LuyenNgheActivity extends AppCompatActivity {
    String DATABASE_NAME = "LuyenNghe db.db";
    SQLiteDatabase database;

    ArrayList<BaiNghe> arrayList;
    LuyenNgheAdapter adapter;
    RecyclerView rv;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luyen_nghe);

        anhxa();
        docdulieu();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LuyenNgheActivity.this, "tim kiem", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void anhxa() {
        rv = findViewById(R.id.rv_bainghe);
        rv.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter = new LuyenNgheAdapter(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LuyenNgheActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager); // set kieu sap xep cac item trong recyclerview

        rv.setAdapter(adapter);

        button = findViewById(R.id.button_zxczc);
    }

    private void docdulieu() {
        database = DataBase.initDatabase(LuyenNgheActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Luyennghe", null);
        arrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String tentopic = cursor.getString(0);
            byte[] hinh = cursor.getBlob(1);
            String link = cursor.getString(2);
            String script = cursor.getString(3);
            arrayList.add(new BaiNghe(tentopic, hinh,link,script));
        }
        adapter.notifyDataSetChanged();
    }

}