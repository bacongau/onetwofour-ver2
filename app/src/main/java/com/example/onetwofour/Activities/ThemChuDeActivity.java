package com.example.onetwofour.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onetwofour.Adapter.NguPhapAdapter;
import com.example.onetwofour.Model.MauCau;
import com.example.onetwofour.Model.NguPhap;
import com.example.onetwofour.Model.TuVung;
import com.example.onetwofour.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThemChuDeActivity extends AppCompatActivity {
    DatabaseReference mDatabase;

    EditText edt_chude;
    Button btn_timchude;
    RecyclerView rv;
    FloatingActionButton fab;

    ArrayList<NguPhap> arrayList;
    ArrayList<NguPhap> arrayListSearch;
    ArrayList<NguPhap> arrayListEditor;
    NguPhapAdapter adapter, adapterSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_chu_de);

        anhxa();
        laydulieu();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ThemChuDeActivity.this);
                dialog.setContentView(R.layout.dialog_themchude);

                EditText edt_tenchudemoi = dialog.findViewById(R.id.edt_tenchudemoi);
                EditText edt_linkhinhchude = dialog.findViewById(R.id.edt_linkhinhchude);
                Button btn_ok = dialog.findViewById(R.id.btn_dialogthemchude_ok);
                Button btn_huy = dialog.findViewById(R.id.btn_dialogthemchude_huy);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = edt_tenchudemoi.getText().toString();
                        String b = edt_linkhinhchude.getText().toString();

                        if (validateDulieu(a,b) == 2){
                            edt_tenchudemoi.setError("Chủ đề đã tồn tại");
                        }else if (validateDulieu(a,b) == 1){
                            edt_tenchudemoi.setError("Không để trống trường này");
                        }else if (validateDulieu(a,b) == 3){
                            edt_linkhinhchude.setError("Không để trống trường này");
                        }else {
                            NguPhap nguPhap = new NguPhap(a,b);
                            mDatabase.child("topic").push().setValue(nguPhap);

                            TuVung tuVung = new TuVung("Phiên âm - ý nghĩa","aaa","Từ vựng 1");
                            MauCau mauCau = new MauCau("This is English script","Đây là phiên âm tiếng Việt");
                            mDatabase.child("ngu phap").child(a).child("mau cau").push().setValue(mauCau);
                            mDatabase.child("ngu phap").child(a).child("tu vung").push().setValue(tuVung);
                            dialog.dismiss();
                            Toast.makeText(ThemChuDeActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        adapter.setOnItemLongClickListener(new NguPhapAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(int position) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ThemChuDeActivity.this);
                builder1.setTitle("Xoá chủ đề");
                builder1.setMessage("Xác nhận xóa ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (arrayList.size() >= 2){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Query topicQuery = ref.child("topic").orderByChild("topicName").equalTo(arrayList.get(position).getTopicName());

                                    topicQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot topicSnapshot: snapshot.getChildren()) {
                                                topicSnapshot.getRef().removeValue();
                                                arrayList.clear();
                                                laydulieu();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Toast.makeText(ThemChuDeActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ThemChuDeActivity.this, "Phải để lại 1 dữ liệu", Toast.LENGTH_SHORT).show();
                                }

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

                return true;
            }
        });


        btn_timchude.setOnClickListener(new View.OnClickListener() {
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
                    adapterSearch = new NguPhapAdapter(arrayListSearch);
                    rv.setAdapter(adapterSearch);
                }
            }
        });
        edt_chude.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) ThemChuDeActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_chude.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }


    private int validateDulieu(String a, String b){
        for (int i = 0;i<arrayList.size();i++){
            if (a.equalsIgnoreCase(arrayList.get(i).getTopicName())){
                return 2;
            }
        }
        if (a.isEmpty()){
            return 1;
        }
        if (b.isEmpty()){
            return 3;
        }

        return 0;
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
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(ThemChuDeActivity.this, "Đã tải xong dữ liệu", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void anhxa() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        fab = findViewById(R.id.fab_themchude);
        edt_chude = findViewById(R.id.edt_themchude_timchude);
        btn_timchude = findViewById(R.id.btn_themchude_timchude);
        rv = findViewById(R.id.rv_themchude_chude);

        arrayList = new ArrayList<>();
        arrayListEditor = new ArrayList<>();
        arrayListSearch = new ArrayList<>();
        adapter = new NguPhapAdapter(arrayList);
        adapterSearch = new NguPhapAdapter(arrayListSearch);

        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThemChuDeActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager); // set kieu sap xep cac item trong recyclerview

        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(ThemChuDeActivity.this,
                DividerItemDecoration.VERTICAL));
    }
}