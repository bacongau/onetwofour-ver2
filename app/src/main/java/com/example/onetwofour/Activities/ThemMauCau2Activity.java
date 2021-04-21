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

import com.example.onetwofour.Adapter.MauCauAdapter;
import com.example.onetwofour.Adapter.TuVungAdapter;
import com.example.onetwofour.Model.MauCau;
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

public class ThemMauCau2Activity extends AppCompatActivity {
    DatabaseReference mDatabase, mDatabase1, mDatabase2;
    ArrayList<MauCau> arrayList;
    RecyclerView rv;
    MauCauAdapter adapter;
    String d;
    TextToSpeech textToSpeech;
    boolean loading = false;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mau_cau2);

        // get topic
        d = getIntent().getStringExtra("topic11");

        mDatabase = FirebaseDatabase.getInstance().getReference("ngu phap");
        mDatabase1 = mDatabase.child(d);
        mDatabase2 = mDatabase1.child("mau cau");

        fab = findViewById(R.id.fab_themmaucau);
        rv = findViewById(R.id.rv_dev_themmaucau);
        rv.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        adapter = new MauCauAdapter(arrayList);

        // set recycleview click item
        textToSpeech = new TextToSpeech(ThemMauCau2Activity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
        adapter.setOnItemClickListener(new MauCauAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String s = arrayList.get(position).getEng();
                int speech = textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        // setup cho recycleview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThemMauCau2Activity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(ThemMauCau2Activity.this,
                DividerItemDecoration.VERTICAL));

        docdulieu();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ThemMauCau2Activity.this);
                dialog.setContentView(R.layout.dialog_themmaucau);

                EditText edt_eng = dialog.findViewById(R.id.edt_dev_maucau_eng);
                EditText edt_viet = dialog.findViewById(R.id.edt_dev_maucau_viet);
                Button btn_ok = dialog.findViewById(R.id.btn_dev_maucau_ok);
                Button btn_huy = dialog.findViewById(R.id.btn_dev_maucau_huy);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = edt_eng.getText().toString();
                        String b = edt_viet.getText().toString();

                        if (validateDulieu(a, b) == 1) {
                            edt_eng.setError("Không để trống trường này");
                        } else if (validateDulieu(a, b) == 2) {
                            edt_viet.setError("Không để trống trường này");
                        } else {
                            MauCau mauCau = new MauCau(a, b);
                            DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
                            dr.child("ngu phap").child(d).child("mau cau").push().setValue(mauCau);

                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(ThemMauCau2Activity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
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
        adapter.setOnItemLongClickListener(new MauCauAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(int position) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ThemMauCau2Activity.this);
                builder1.setTitle("Xoá Mẫu câu này");
                builder1.setMessage("Xác nhận xóa ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (arrayList.size()>=2){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Query topicQuery = ref.child("ngu phap").child(d).child("mau cau").orderByChild("eng").equalTo(arrayList.get(position).getEng());

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

                                    Toast.makeText(ThemMauCau2Activity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ThemMauCau2Activity.this, "Phải để lại 1 dữ liệu", Toast.LENGTH_SHORT).show();
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

    private int validateDulieu(String a, String b) {
        if (b.isEmpty()) {
            return 2;
        }
        if (a.isEmpty()) {
            return 1;
        }

        return 0;
    }

    private void docdulieu() {
        mDatabase2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MauCau mauCau = snapshot.getValue(MauCau.class);
                arrayList.add(mauCau);
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