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
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onetwofour.Adapter.TuVungAdapter;
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
import java.util.Locale;

public class ThemTuVung2Activity extends AppCompatActivity {
    DatabaseReference mDatabase,mDatabase1,mDatabase2;
    ArrayList<TuVung> tuVungArrayList;
    RecyclerView rv;
    TuVungAdapter adapter;
    String d;
    TextToSpeech textToSpeech;
    boolean loading = false;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tu_vung2);

        // get topic
        d = getIntent().getStringExtra("topic10");

        mDatabase = FirebaseDatabase.getInstance().getReference("ngu phap");
        mDatabase1 = mDatabase.child(d);
        mDatabase2 = mDatabase1.child("tu vung");

        fab = findViewById(R.id.fab_themtuvung);
        rv = findViewById(R.id.rv_dev_themtuvung);
        rv.setHasFixedSize(true);
        tuVungArrayList = new ArrayList<>();
        adapter = new TuVungAdapter(tuVungArrayList);

        // set recycleview click item
        textToSpeech = new TextToSpeech(ThemTuVung2Activity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
        adapter.setOnItemClickListener(new TuVungAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String s = tuVungArrayList.get(position).getWord();
                int speech = textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        // setup cho recycleview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThemTuVung2Activity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(ThemTuVung2Activity.this,
                DividerItemDecoration.VERTICAL));

        docdulieu();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ThemTuVung2Activity.this);
                dialog.setContentView(R.layout.dialog_themtuvung);

                EditText edt_tumoi = dialog.findViewById(R.id.edt_dev_tumoi);
                EditText edt_mota = dialog.findViewById(R.id.edt_dev_descmoi);
                EditText edt_hinh = dialog.findViewById(R.id.edt_dev_linkhinhmoi);
                Button btn_ok = dialog.findViewById(R.id.btn_dev_tumoi_ok);
                Button btn_huy = dialog.findViewById(R.id.btn_dev_tumoi_huy);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = edt_tumoi.getText().toString();
                        String b = edt_mota.getText().toString();
                        String c = edt_hinh.getText().toString();

                        if (validateDulieu(a,b,c) == 2){
                            edt_tumoi.setError("Từ này đã tồn tại");
                        }else if (validateDulieu(a,b,c) == 1){
                            edt_tumoi.setError("Không để trống trường này");
                        }else if (validateDulieu(a,b,c) == 3){
                            edt_hinh.setError("Không để trống trường này");
                        }else {
                            TuVung tuVung = new TuVung(b,c,a);
                            DatabaseReference drTuVung = FirebaseDatabase.getInstance().getReference();
                            drTuVung.child("ngu phap").child(d).child("tu vung").push().setValue(tuVung);

                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(ThemTuVung2Activity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
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
        adapter.setOnItemLongClickListener(new TuVungAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(int position) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ThemTuVung2Activity.this);
                builder1.setTitle("Xoá Từ vựng");
                builder1.setMessage("Xác nhận xóa ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (tuVungArrayList.size()>=2){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Query topicQuery = ref.child("ngu phap").child(d).child("tu vung").orderByChild("word").equalTo(tuVungArrayList.get(position).getWord());

                                    topicQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot topicSnapshot: snapshot.getChildren()) {
                                                topicSnapshot.getRef().removeValue();
                                                tuVungArrayList.clear();
                                                docdulieu();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Toast.makeText(ThemTuVung2Activity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ThemTuVung2Activity.this, "Phải để lại 1 dữ liệu", Toast.LENGTH_SHORT).show();
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

    private int validateDulieu(String a,String b,String c){
        for (int i = 0;i<tuVungArrayList.size();i++){
            if (a.equalsIgnoreCase(tuVungArrayList.get(i).getWord())){
                return 2;
            }
        }
        if (a.isEmpty()){
            return 1;
        }
        if (c.isEmpty()){
            return 3;
        }

        return 0;
    }

    private void docdulieu() {
        mDatabase2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TuVung tuVung = snapshot.getValue(TuVung.class);
                tuVungArrayList.add(tuVung);
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
}