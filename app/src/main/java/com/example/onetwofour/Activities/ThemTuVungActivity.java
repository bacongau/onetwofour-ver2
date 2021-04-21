package com.example.onetwofour.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.onetwofour.Adapter.NguPhapAdapter;
import com.example.onetwofour.Model.NguPhap;
import com.example.onetwofour.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ThemTuVungActivity extends AppCompatActivity {
    DatabaseReference mDatabase;
    RecyclerView rv;
    ArrayList<NguPhap> arrayList;
    NguPhapAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tu_vung);

        anhxa();
        laydulieu();

        adapter.setOnItemClickListener(new NguPhapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ThemTuVungActivity.this,ThemTuVung2Activity.class);
                intent.putExtra("topic10",arrayList.get(position).getTopicName());
                startActivity(intent);
            }
        });
    }


    private void laydulieu() {
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
    }

    private void anhxa() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        rv = findViewById(R.id.rv_dev_dschude_themtv);
        arrayList = new ArrayList<>();
        adapter = new NguPhapAdapter(arrayList);

        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThemTuVungActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager); // set kieu sap xep cac item trong recyclerview

        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(ThemTuVungActivity.this,
                DividerItemDecoration.VERTICAL));
    }
}