package com.example.onetwofour.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.onetwofour.Model.BaiDoc;
import com.example.onetwofour.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BaiDocActivity extends AppCompatActivity {
    DatabaseReference mDatabase;

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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        tv_eng = findViewById(R.id.tv_script_eng);
        tv_viet = findViewById(R.id.tv_script_viet);
        sv_eng = findViewById(R.id.sv_engsub);
        sv_viet = findViewById(R.id.sv_vietsub);
        baiDocArrayList = new ArrayList<>();
    }

    private void docdulieu() {
        mDatabase.child("bai doc").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BaiDoc baiDoc = snapshot.getValue(BaiDoc.class);
                baiDocArrayList.add(baiDoc);
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
                BaiDoc baiDoc = new BaiDoc();
                for (int i = 0;i<baiDocArrayList.size();i++){
                    if (baiDocArrayList.get(i).getTen().equalsIgnoreCase(a)){
                        baiDoc = baiDocArrayList.get(i);
                    }
                }
                tv_viet.setText(baiDoc.getViet());
                tv_eng.setText(baiDoc.getEng());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}