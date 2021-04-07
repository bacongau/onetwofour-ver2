package com.example.onetwofour.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.onetwofour.Activities.NguPhapActivity;
import com.example.onetwofour.Activities.TuVung_MauCau_Activity;
import com.example.onetwofour.Adapter.MauCauAdapter;
import com.example.onetwofour.Adapter.TuVungAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.MauCau;
import com.example.onetwofour.Model.TuVung;
import com.example.onetwofour.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;


public class MauCau_Fragment extends Fragment {
    DatabaseReference mDatabase,mDatabase1,mDatabase2;

    ArrayList<MauCau> mauCauArrayList;
    RecyclerView rv;
    MauCauAdapter adapter;
    String a;
    TextToSpeech textToSpeech;
    boolean loading = false;


    public MauCau_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mau_cau_, container, false);

        // get topic
        TuVung_MauCau_Activity activity = (TuVung_MauCau_Activity) getActivity();
        a = activity.getMyData();

        mDatabase = FirebaseDatabase.getInstance().getReference("ngu phap");
        mDatabase1 = mDatabase.child(a);
        mDatabase2 = mDatabase1.child("mau cau");

        rv = view.findViewById(R.id.rv_maucau);
        rv.setHasFixedSize(true);
        mauCauArrayList = new ArrayList<>();
        adapter = new MauCauAdapter(mauCauArrayList);

        // set recycleview click item
        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
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
                String s = mauCauArrayList.get(position).getEng();
                int speech = textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        // setup cho recycleview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        docdulieu();


        return view;
    }

    private void docdulieu() {
        mDatabase2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MauCau mauCau = snapshot.getValue(MauCau.class);
                mauCauArrayList.add(mauCau);
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
        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loading = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}