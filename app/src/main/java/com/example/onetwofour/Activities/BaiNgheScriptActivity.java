package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.BaiNghe;
import com.example.onetwofour.R;

import java.util.ArrayList;

public class BaiNgheScriptActivity extends AppCompatActivity {
    String DATABASE_NAME = "LuyenNghe db.db";
    SQLiteDatabase database;

    ArrayList<BaiNghe> arrayList;
    BaiNghe baiNghe;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_nghe_script);

        tv = findViewById(R.id.tv_script_bainghe);
        arrayList = new ArrayList<>();

        laydulieu();
        String b = getMyData();

        for (int i = 0;i<arrayList.size();i++){
            if (b.equals(arrayList.get(i).getTen())){
                baiNghe = arrayList.get(i);
            }
        }

        tv.setText(baiNghe.getScript());
    }

    public String getMyData() {
        Bundle bundle = getIntent().getExtras();
        String a = (String) bundle.get("scriptBaiNghe");
        return a;
    }

    private void laydulieu() {
        database = DataBase.initDatabase(BaiNgheScriptActivity.this, DATABASE_NAME);
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
}