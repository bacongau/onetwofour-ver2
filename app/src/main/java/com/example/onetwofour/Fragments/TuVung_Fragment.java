package com.example.onetwofour.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.onetwofour.Activities.NguPhapActivity;
import com.example.onetwofour.Activities.TuVung_MauCau_Activity;
import com.example.onetwofour.Adapter.TuVungAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.TopicNguPhap;
import com.example.onetwofour.Model.TuVung;
import com.example.onetwofour.R;

import java.util.ArrayList;


public class TuVung_Fragment extends Fragment {
    String DATABASE_NAME = "NguPhap db.db";
    SQLiteDatabase database;
    ArrayList<TuVung> tuVungArrayList;
    ListView listView;
    TuVungAdapter adapter;
    String a;

    public TuVung_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tu_vung_, container, false);

        listView = view.findViewById(R.id.lv_tuvung);
        tuVungArrayList = new ArrayList<>();
        adapter = new TuVungAdapter(getContext(), R.layout.item_tuvung, tuVungArrayList);
        listView.setAdapter(adapter);

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