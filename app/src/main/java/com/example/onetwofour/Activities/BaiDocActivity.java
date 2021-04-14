package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onetwofour.Adapter.LuyenDocViewPagerAdapter;
import com.example.onetwofour.Adapter.NguPhapViewPagerAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.BaiDoc;
import com.example.onetwofour.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BaiDocActivity extends AppCompatActivity {
    String DATABASE_NAME = "LuyenDoc db.db";
    SQLiteDatabase database;

    TextView tv_eng,tv_viet;
    ArrayList<BaiDoc> baiDocArrayList;
    ScrollView sv_eng,sv_viet;

    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_doc);

        anhxa();
        a = getMyData();
        docdulieu();

        tv_viet.setText(baiDocArrayList.get(0).getVietsub());
        tv_eng.setText(baiDocArrayList.get(0).getEngsub());

        sv_eng.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                sv_viet.setScrollY(scrollY);
            }
        });
    }

    public String getMyData() {
        Bundle bundle = getIntent().getExtras();
        String a = (String) bundle.get("topicbaidoc");
        return a;
    }

    private void anhxa() {
        tv_eng = findViewById(R.id.tv_script_eng);
        tv_viet = findViewById(R.id.tv_script_viet);
        sv_eng = findViewById(R.id.sv_engsub);
        sv_viet = findViewById(R.id.sv_vietsub);
        baiDocArrayList = new ArrayList<>();
    }

    private void docdulieu() {
        database = DataBase.initDatabase(BaiDocActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Luyendoc WHERE ten = '" + a + "'", null);
        baiDocArrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String tentopic = cursor.getString(0);
            byte[] hinh = cursor.getBlob(1);
            String engsub = cursor.getString(2);
            String vietsub = cursor.getString(3);
            baiDocArrayList.add(new BaiDoc(tentopic, hinh,engsub,vietsub));
        }
    }
}