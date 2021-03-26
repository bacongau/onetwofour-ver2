package com.example.onetwofour.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onetwofour.Model.MauCau;
import com.example.onetwofour.Model.TopicNguPhap;
import com.example.onetwofour.R;

import java.util.ArrayList;

public class MauCauAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<MauCau> arrayList;

    public MauCauAdapter(Context context, int layout, ArrayList<MauCau> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView tv_eng,tv_viet;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            viewHolder.tv_eng = convertView.findViewById(R.id.tv_maucau_eng);
            viewHolder.tv_viet = convertView.findViewById(R.id.tv_maucau_viet);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_viet.setText(arrayList.get(position).getVietSub());
        viewHolder.tv_eng.setText(arrayList.get(position).getEngSub());

        return convertView;
    }
}
