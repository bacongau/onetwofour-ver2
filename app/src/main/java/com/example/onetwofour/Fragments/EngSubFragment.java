package com.example.onetwofour.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.onetwofour.Activities.BaiDocActivity;
import com.example.onetwofour.Adapter.TuVungAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.BaiDoc;
import com.example.onetwofour.Model.TuVung;
import com.example.onetwofour.R;

import java.util.ArrayList;


public class EngSubFragment extends Fragment {
    String DATABASE_NAME = "LuyenDoc db.db";
    SQLiteDatabase database;
    ArrayList<BaiDoc> baiDocArrayList;
    TextView tv;
    String a;

    public EngSubFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eng_sub, container, false);

        tv = view.findViewById(R.id.tv_eng_script);
        baiDocArrayList = new ArrayList<>();

        BaiDocActivity activity = (BaiDocActivity) getActivity();
        a = activity.getMyData();
        docdulieu();

        tv.setText(baiDocArrayList.get(0).getEngsub());

        return view;
    }

    private void docdulieu() {
        database = DataBase.initDatabase(getActivity(), DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Luyendoc WHERE ten = '" + a + "'", null);
        baiDocArrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String tentopic = cursor.getString(0);
            byte[] hinh = cursor.getBlob(1);
            String engsub = cursor.getString(2);
            String vietsub = cursor.getString(3);
            baiDocArrayList.add(new BaiDoc(tentopic, hinh,engsub,vietsub));
        }
    }
}