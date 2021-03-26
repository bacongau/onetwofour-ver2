package com.example.onetwofour.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.onetwofour.Activities.TuVung_MauCau_Activity;
import com.example.onetwofour.Adapter.MauCauAdapter;
import com.example.onetwofour.Adapter.TuVungAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.MauCau;
import com.example.onetwofour.Model.TuVung;
import com.example.onetwofour.R;

import java.util.ArrayList;


public class MauCau_Fragment extends Fragment {
    String DATABASE_NAME = "NguPhap db.db";
    SQLiteDatabase database;
    ArrayList<MauCau> mauCauArrayList;
    ListView listView;
    MauCauAdapter adapter;
    String a;

    public MauCau_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mau_cau_, container, false);

        listView = view.findViewById(R.id.lv_maucau);
        mauCauArrayList = new ArrayList<>();
        adapter = new MauCauAdapter(getContext(), R.layout.item_maucau, mauCauArrayList);
        listView.setAdapter(adapter);

        // get topic
        TuVung_MauCau_Activity activity = (TuVung_MauCau_Activity) getActivity();
        a = activity.getMyData();

        docdulieu();

        return view;
    }

    private void docdulieu() {
        database = DataBase.initDatabase(getActivity(), DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Maucau WHERE topic = '" + a + "'", null); // doi school bang topic trong intent
        mauCauArrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String tentopic = cursor.getString(0);
            String engsub = cursor.getString(1);
            String vietsub = cursor.getString(2);
            mauCauArrayList.add(new MauCau(tentopic, engsub, vietsub));
        }
        adapter.notifyDataSetChanged();
    }
}