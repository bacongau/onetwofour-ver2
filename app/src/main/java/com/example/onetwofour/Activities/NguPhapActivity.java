package com.example.onetwofour.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onetwofour.Adapter.NguPhapAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Database.DbHelper;
import com.example.onetwofour.Model.MyVocab;
import com.example.onetwofour.Model.NguPhap;
import com.example.onetwofour.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NguPhapActivity extends AppCompatActivity {
    DatabaseReference mDatabase;
    SQLiteDatabase db;

    ArrayList<NguPhap> arrayList;
    NguPhapAdapter adapter, adapterSearch;
    RecyclerView rv;
    Toolbar myToolbar;
    Button btn_search;
    EditText edt_chude;
    ArrayList<NguPhap> arrayListSearch;
    ArrayList<MyVocab> vocabArrayList;

    boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngu_phap);

        anhxa();
        docdulieu();
        setUpToolbar();

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

                    rv.setAdapter(adapterSearch);
                }
            }
        });
        edt_chude.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) NguPhapActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_chude.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        adapter.setOnItemClickListener(new NguPhapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(NguPhapActivity.this,TuVung_MauCau_Activity.class);
                intent.putExtra("topic",arrayList.get(position).getTopicName());
                startActivity(intent);
            }
        });
        adapterSearch.setOnItemClickListener(new NguPhapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(NguPhapActivity.this,TuVung_MauCau_Activity.class);
                intent.putExtra("topic",arrayListSearch.get(position).getTopicName());
                startActivity(intent);
            }
        });
    }


    private void docdulieu() {
        mDatabase.child("topic").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                NguPhap nguPhap = snapshot.getValue(NguPhap.class);
                arrayList.add(nguPhap);
                adapter.notifyDataSetChanged();
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
                Toast.makeText(NguPhapActivity.this, "Đã tải xong dữ liệu", Toast.LENGTH_SHORT).show();
                loading = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void anhxa() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btn_search = findViewById(R.id.btn_nguphap_find_topic);
        edt_chude = (EditText) NguPhapActivity.this.findViewById(R.id.edt_nguphap_find_topic);
        arrayListSearch = new ArrayList<>();
        adapterSearch = new NguPhapAdapter(arrayListSearch);

        rv = findViewById(R.id.rv_topic_nguphap);
        rv.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter = new NguPhapAdapter(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NguPhapActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager); // set kieu sap xep cac item trong recyclerview

        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(NguPhapActivity.this,
                DividerItemDecoration.VERTICAL));

        vocabArrayList = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(NguPhapActivity.this);
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
        switch (item.getItemId()) {
            case R.id.action_them_tu_vung: {
                hienDialogThemTuVung();
                break;
            }
            case R.id.action_xem_tu_vung: {
                startActivity(new Intent(NguPhapActivity.this,MyVocabularyActivity.class));
                break;
            }
            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void hienDialogThemTuVung() {
        Dialog dialog = new Dialog(NguPhapActivity.this);
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
                    Toast.makeText(NguPhapActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
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


