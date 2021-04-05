package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.onetwofour.Adapter.LuyenDocAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.BaiDoc;
import com.example.onetwofour.R;

import java.util.ArrayList;

public class LuyenDocActivity extends AppCompatActivity {
    String DATABASE_NAME = "LuyenDoc db.db";
    SQLiteDatabase database;

    ArrayList<BaiDoc> arrayList;
    LuyenDocAdapter adapter, adapterSearch;
    RecyclerView rv;
    Button button;
    EditText edt_baidoc;
    ArrayList<BaiDoc> arrayListSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luyen_doc);

        anhxa();
        docdulieu();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String b = edt_baidoc.getText().toString();
                arrayListSearch.clear();
                for (int i = 0; i < arrayList.size(); i++) {
                    if (b.equalsIgnoreCase(arrayList.get(i).getTenbaidoc())) {
                        arrayListSearch.add(arrayList.get(i));
                    }
                }

                if (b.equalsIgnoreCase("")) {
                    rv.setAdapter(adapter);
                } else {
                    adapterSearch = new LuyenDocAdapter( arrayListSearch);
                    rv.setAdapter(adapterSearch);
                }

            }
        });
    }

    private void docdulieu() {
        database = DataBase.initDatabase(LuyenDocActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Luyendoc", null);
        arrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String tentopic = cursor.getString(0);
            byte[] hinh = cursor.getBlob(1);
            String engsub = cursor.getString(2);
            String vietsub = cursor.getString(3);
            arrayList.add(new BaiDoc(tentopic, hinh,engsub,vietsub));
        }
        adapter.notifyDataSetChanged();
    }

    private void anhxa() {
        button = findViewById(R.id.button_zxczc2);
        edt_baidoc = findViewById(R.id.edt_find_baidoc);
        arrayList = new ArrayList<>();
        arrayListSearch = new ArrayList<>();

        rv = findViewById(R.id.rv_baidoc);
        rv.setHasFixedSize(true);
        adapter = new LuyenDocAdapter(arrayList);

        // set recycleview click item
        adapter.setOnItemClickListener(new LuyenDocAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(LuyenDocActivity.this, BaiDocActivity.class);
                intent.putExtra("topicbaidoc", arrayList.get(position).getTenbaidoc());
                startActivity(intent);
            }
        });

        // setup cho recycleview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        rv.setAdapter(adapter);
    }
}