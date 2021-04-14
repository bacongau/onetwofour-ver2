package com.example.onetwofour.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onetwofour.Adapter.NguPhapViewPagerAdapter;
import com.example.onetwofour.Database.DbHelper;
import com.example.onetwofour.Fragments.MauCau_Fragment;
import com.example.onetwofour.Fragments.TuVung_Fragment;
import com.example.onetwofour.Model.MyVocab;
import com.example.onetwofour.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TuVung_MauCau_Activity extends AppCompatActivity {
    SQLiteDatabase db;

    TabLayout tabLayout;
    ViewPager viewPager;
    NguPhapViewPagerAdapter pagerAdapter;
    Toolbar myToolbar;
    ArrayList<MyVocab> vocabArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu_vung__mau_cau_);

        anhxa();
        setUpToolbar();

    }

    public String getMyData() {
        Bundle bundle = getIntent().getExtras();
        String a = (String) bundle.get("topic");
        return a;
    }

    private void anhxa() {
        tabLayout = findViewById(R.id.tablayout_nguphap);
        viewPager = findViewById(R.id.viewpager_nguphap);
        pagerAdapter = new NguPhapViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        vocabArrayList = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(TuVung_MauCau_Activity.this);
        db = dbHelper.getWritableDatabase();
    }

    private void setUpToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_them_tu_vung:{
                hienDialogThemTuVung();
                break;
            }
            case R.id.action_xem_tu_vung:{
                startActivity(new Intent(TuVung_MauCau_Activity.this,MyVocabularyActivity.class));
                break;
            }
            default:{
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void hienDialogThemTuVung() {
        Dialog dialog = new Dialog(TuVung_MauCau_Activity.this);
        dialog.setContentView(R.layout.dialog_them_tu_vung);

        EditText edt_word = dialog.findViewById(R.id.edt_keyword);
        EditText edt_mota = dialog.findViewById(R.id.edt_mota);
        Button btn_ok = dialog.findViewById(R.id.btn_dialog_them_tuvung);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = edt_word.getText().toString();
                String b = edt_mota.getText().toString();

                String sql = "SELECT * FROM MyVocab";
                vocabArrayList = getData(sql);

                if (validateEditText(a, b) == false) {
                    edt_word.setError("Không được để trống");
                } else if (checkTrung(vocabArrayList, a) == false) {
                    edt_word.setError("Từ đã tồn tại");
                } else {

                    ContentValues values = new ContentValues();
                    values.put("keyword", a);
                    values.put("mota", b);
                    MyVocab myVocab = new MyVocab(a, b);
                    db.insert("MyVocab", null, values);
                    Toast.makeText(TuVung_MauCau_Activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private boolean validateEditText(String a, String b) {
        if (a.isEmpty() || a.trim().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkTrung(ArrayList<MyVocab> vocabArrayList, String a){
        for (int i = 0 ; i< vocabArrayList.size();i++){
            if (a.equals(vocabArrayList.get(i).getKeyword())){
                return false;
            }
        }

        return true;
    }

    private ArrayList<MyVocab> getData(String sql, String... selectionArgs) {
        ArrayList<MyVocab> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            MyVocab obj = new MyVocab();
            obj.setKeyword(String.valueOf(cursor.getString(cursor.getColumnIndex("keyword"))));
            obj.setMota(String.valueOf(cursor.getString(cursor.getColumnIndex("mota"))));
            list.add(obj);
        }
        return list;
    }
}