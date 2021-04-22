package com.example.onetwofour.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onetwofour.Adapter.LuyenNgheAdapter;
import com.example.onetwofour.Model.BaiNghe;
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

import java.io.IOException;
import java.util.ArrayList;

public class ThemBaiNgheActivity extends AppCompatActivity {
    DatabaseReference mDatabase;

    ArrayList<BaiNghe> arrayList;
    LuyenNgheAdapter adapter;

    RecyclerView rv;
    FloatingActionButton fab;

    boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_bai_nghe);

        anhxa();
        docdulieu();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ThemBaiNgheActivity.this);
                dialog.setContentView(R.layout.dialog_thembainghe);

                EditText edt_ten = dialog.findViewById(R.id.edt_dev_bainghemoi_ten);
                EditText edt_linkaudio = dialog.findViewById(R.id.edt_dev_bainghemoi_linkaudio);
                EditText edt_linkhinh = dialog.findViewById(R.id.edt_dev_bainghemoi_hinh);
                EditText edt_script = dialog.findViewById(R.id.edt_dev_bainghemoi_script);
                Button btn_ok = dialog.findViewById(R.id.btn_dev_thembainghe_ok);
                Button btn_huy = dialog.findViewById(R.id.btn_dev_thembainghe_huy);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = edt_ten.getText().toString();
                        String b = edt_linkaudio.getText().toString();
                        String c = edt_linkhinh.getText().toString();
                        String d = edt_script.getText().toString();

                        if (validateDulieu(a,b,c,d) == 2){
                            edt_ten.setError("Không để trống trường này");
                        }else if (validateDulieu(a,b,c,d) == 1){
                            edt_ten.setError("Bài nghe đã tồn tại");
                        }else if (validateDulieu(a,b,c,d) == 3){
                            edt_linkaudio.setError("Không để trống trường này");
                        }else if (validateDulieu(a,b,c,d) == 4){
                            edt_linkaudio.setError("Link không hợp lệ");
                        }else {
                            BaiNghe baiNghe = new BaiNghe(a,c,b,d);
                            mDatabase.child("bai nghe 1").push().setValue(baiNghe);

                            dialog.dismiss();
                            Toast.makeText(ThemBaiNgheActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
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
        adapter.setOnItemLongClickListener(new LuyenNgheAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(int position) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ThemBaiNgheActivity.this);
                builder1.setTitle("Xoá bài nghe");
                builder1.setMessage("Xác nhận xóa ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (arrayList.size() >= 2){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Query topicQuery = ref.child("bai nghe 1").orderByChild("ten").equalTo(arrayList.get(position).getTen());

                                    topicQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot topicSnapshot: snapshot.getChildren()) {
                                                topicSnapshot.getRef().removeValue();
                                                arrayList.clear();
                                                docdulieu();
                                                adapter.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Toast.makeText(ThemBaiNgheActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ThemBaiNgheActivity.this, "Phải để lại 1 dữ liệu", Toast.LENGTH_SHORT).show();
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
    }

    private int validateDulieu(String a,String b,String c,String d){
        for (int i = 0;i<arrayList.size();i++){
            if (a.equalsIgnoreCase(arrayList.get(i).getTen())){
                return 1;
            }
        }
        if (khoiTaoMedia(b) == false){
            return 4;
        }

        if (a.isEmpty()){
            return 2;
        }
        if (b.isEmpty()){
            return 3;
        }

        return 0;
    }

    private boolean khoiTaoMedia(String b) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(b);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void anhxa() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        rv = findViewById(R.id.rv_dsbainghe_thembainghe);
        fab = findViewById(R.id.fab_thembainghe);

        rv.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter = new LuyenNgheAdapter(arrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThemBaiNgheActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager); // set kieu sap xep cac item trong recyclerview

        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(ThemBaiNgheActivity.this,
                DividerItemDecoration.VERTICAL));
    }

    private void docdulieu() {
        mDatabase.child("bai nghe 1").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BaiNghe baiNghe = snapshot.getValue(BaiNghe.class);
                arrayList.add(baiNghe);
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
                Toast.makeText(ThemBaiNgheActivity.this, "Đã tải xong dữ liệu", Toast.LENGTH_SHORT).show();
                loading = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}