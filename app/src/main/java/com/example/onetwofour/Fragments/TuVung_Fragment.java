package com.example.onetwofour.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.onetwofour.Activities.NguPhapActivity;
import com.example.onetwofour.Activities.TuVung_MauCau_Activity;
import com.example.onetwofour.Adapter.TuVungAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.TuVung;
import com.example.onetwofour.R;

import java.util.ArrayList;
import java.util.Locale;


public class TuVung_Fragment extends Fragment {
    String DATABASE_NAME = "NguPhap db.db";
    SQLiteDatabase database;
    ArrayList<TuVung> tuVungArrayList;
    RecyclerView rv;
    TuVungAdapter adapter;
    String a;
    TextToSpeech textToSpeech;

    public TuVung_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tu_vung_, container, false);

        rv = view.findViewById(R.id.rv_tuvung);
        rv.setHasFixedSize(true);
        tuVungArrayList = new ArrayList<>();
        adapter = new TuVungAdapter(tuVungArrayList);

        // set recycleview click item
        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        rv.setAdapter(adapter);

        // get topic
        TuVung_MauCau_Activity activity = (TuVung_MauCau_Activity) getActivity();
        a = activity.getMyData();

        docdulieu();

        return view;
    }

    private void docdulieu() {
        database = DataBase.initDatabase(getActivity(), DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Tuvung WHERE topic = '" + a + "'", null);
        tuVungArrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String tentopic = cursor.getString(0);
            byte[] hinh = cursor.getBlob(1);
            String desc = cursor.getString(2);
            String linkaudio = cursor.getString(3);
            tuVungArrayList.add(new TuVung(tentopic, hinh, desc, linkaudio));
        }
        adapter.notifyDataSetChanged();
    }


}