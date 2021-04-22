package com.example.onetwofour.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.onetwofour.Adapter.LuyenDocAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.BaiDoc;
import com.example.onetwofour.Model.BaiNghe;
import com.example.onetwofour.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LuyenDocActivity extends AppCompatActivity {
    DatabaseReference mDatabase;

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
                    if (b.equalsIgnoreCase(arrayList.get(i).getTen())) {
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
        edt_baidoc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) LuyenDocActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_baidoc.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    private void docdulieu() {
        mDatabase.child("bai doc").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BaiDoc baiDoc = snapshot.getValue(BaiDoc.class);
                arrayList.add(baiDoc);
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
    }

    private void anhxa() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        button = findViewById(R.id.button_zxczc2);
        edt_baidoc = findViewById(R.id.edt_find_baidoc);
        arrayList = new ArrayList<>();
        arrayListSearch = new ArrayList<>();

        rv = findViewById(R.id.rv_baidoc);
        rv.setHasFixedSize(true);
        adapter = new LuyenDocAdapter(arrayList);
        adapterSearch = new LuyenDocAdapter(arrayListSearch);

        // set recycleview click item
        adapter.setOnItemClickListener(new LuyenDocAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(LuyenDocActivity.this, BaiDocActivity.class);
                intent.putExtra("topicbaidoc", arrayList.get(position).getTen());
                startActivity(intent);
            }
        });

        adapterSearch.setOnItemClickListener(new LuyenDocAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(LuyenDocActivity.this, BaiDocActivity.class);
                intent.putExtra("topicbaidoc", arrayList.get(position).getTen());
                startActivity(intent);
            }
        });

        // setup cho recycleview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        rv.setAdapter(adapter);

        rv.addItemDecoration(new DividerItemDecoration(LuyenDocActivity.this,
                DividerItemDecoration.VERTICAL));
    }
}