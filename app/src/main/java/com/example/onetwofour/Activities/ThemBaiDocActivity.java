package com.example.onetwofour.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onetwofour.Adapter.LuyenDocAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.BaiDoc;
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

public class ThemBaiDocActivity extends AppCompatActivity {
    DatabaseReference mDatabase;

    ArrayList<BaiDoc> arrayList;
    LuyenDocAdapter adapter;
    RecyclerView rv;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_bai_doc);

        anhxa();
        docdulieu();


        adapter.setOnItemLongClickListener(new LuyenDocAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(int position) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ThemBaiDocActivity.this);
                builder1.setTitle("Xoá bài đọc");
                builder1.setMessage("Xác nhận xóa ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (arrayList.size() >= 2){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Query topicQuery = ref.child("bai doc").orderByChild("ten").equalTo(arrayList.get(position).getTen());

                                    topicQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot topicSnapshot: snapshot.getChildren()) {
                                                topicSnapshot.getRef().removeValue();
                                                arrayList.clear();
                                                docdulieu();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Toast.makeText(ThemBaiDocActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ThemBaiDocActivity.this, "Phải để lại 1 dữ liệu", Toast.LENGTH_SHORT).show();
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ThemBaiDocActivity.this);
                dialog.setContentView(R.layout.dialog_thembaidocmoi);

                EditText edt_ten = dialog.findViewById(R.id.edt_dev_baidocmoi_ten);
                EditText edt_hinh = dialog.findViewById(R.id.edt_dev_baidocmoi_hinh);
                EditText edt_engsub = dialog.findViewById(R.id.edt_dev_baidocmoi_engsub );
                EditText edt_vietsub = dialog.findViewById(R.id.edt_dev_baidocmoi_vietsub);
                Button btn_ok = dialog.findViewById(R.id.btn_dev_thembaidoc_ok);
                Button btn_huy = dialog.findViewById(R.id.btn_dev_thembaidoc_huy);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = edt_ten.getText().toString();
                        String b = edt_hinh.getText().toString();
                        String c = edt_engsub.getText().toString();
                        String d = edt_vietsub.getText().toString();

                        if (validateDulieu(a,b,c,d) == 1){
                            edt_ten.setError("Không để trống trường này");
                        }else if (validateDulieu(a,b,c,d) == 2){
                            edt_ten.setError("Bài này đã tồn tại");
                        }else if (validateDulieu(a,b,c,d) == 3){
                            edt_hinh.setError("Không để trống trường này");
                        }else {
                            BaiDoc baiDoc = new BaiDoc(a,b,c,d);
                            mDatabase.child("bai doc").push().setValue(baiDoc);

                            dialog.dismiss();
                            Toast.makeText(ThemBaiDocActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
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
    }

    private int validateDulieu(String a,String b,String c,String d){
        for (int i = 0;i<arrayList.size();i++){
            if (a.equalsIgnoreCase(arrayList.get(i).getTen())){
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
        fab = findViewById(R.id.fab_thembaidoc);
        arrayList = new ArrayList<>();
        rv = findViewById(R.id.rv_dev_dsbaidoc);
        rv.setHasFixedSize(true);
        adapter = new LuyenDocAdapter(arrayList);


        // setup cho recycleview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        rv.setAdapter(adapter);

        rv.addItemDecoration(new DividerItemDecoration(ThemBaiDocActivity.this,
                DividerItemDecoration.VERTICAL));
    }
}