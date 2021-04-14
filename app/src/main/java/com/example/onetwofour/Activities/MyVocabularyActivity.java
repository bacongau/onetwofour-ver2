package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.onetwofour.Adapter.MyVocabAdapter;
import com.example.onetwofour.Adapter.NguPhapAdapter;
import com.example.onetwofour.Database.DbHelper;
import com.example.onetwofour.Model.MyVocab;
import com.example.onetwofour.R;

import java.util.ArrayList;

public class MyVocabularyActivity extends AppCompatActivity {
    SQLiteDatabase db;

    RecyclerView rv;
    ArrayList<MyVocab> vocabArrayList;
    MyVocabAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vocabulary);

        DbHelper dbHelper = new DbHelper(MyVocabularyActivity.this);
        db = dbHelper.getWritableDatabase();

        vocabArrayList = new ArrayList<>();
        String sql = "SELECT * FROM MyVocab";
        vocabArrayList = getData(sql);


        rv = findViewById(R.id.rv_myvocab);
        rv.setHasFixedSize(true);
        adapter = new MyVocabAdapter(vocabArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyVocabularyActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager); // set kieu sap xep cac item trong recyclerview

        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(MyVocabularyActivity.this,
                DividerItemDecoration.VERTICAL));

        adapter.setOnItemClickListener(new MyVocabAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MyVocabularyActivity.this);
                builder1.setTitle("Xóa từ vựng này");
                builder1.setMessage("Bạn chắc chắn muốn xóa ?");

                builder1.setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String a = vocabArrayList.get(position).getKeyword();
                                db.delete("MyVocab","keyword=?",new String[]{a});

                                vocabArrayList.remove(vocabArrayList.get(position));
                                adapter.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Không",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.setCanceledOnTouchOutside(false);
                alert11.show();
            }
        });
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