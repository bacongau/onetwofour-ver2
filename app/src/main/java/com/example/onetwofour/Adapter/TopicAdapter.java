package com.example.onetwofour.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onetwofour.Activities.TuVung_MauCau_Activity;
import com.example.onetwofour.Model.TopicNguPhap;
import com.example.onetwofour.R;

import java.util.ArrayList;

public class TopicAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<TopicNguPhap> arrayList;

    public TopicAdapter(Context context, int layout, ArrayList<TopicNguPhap> arrayList) {
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
        ImageView img;
        TextView tv;
        Button btn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            viewHolder.img = convertView.findViewById(R.id.img_topic_nguphap);
            viewHolder.tv = convertView.findViewById(R.id.tv_topic_nguphap);
            viewHolder.btn = convertView.findViewById(R.id.btn_topic_nguphap);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        byte[] Hinh = arrayList.get(position).getImg();
        Bitmap bm_hinh = BitmapFactory.decodeByteArray(Hinh,0,Hinh.length);
        viewHolder.img.setImageBitmap(bm_hinh);


        viewHolder.tv.setText(arrayList.get(position).getTopicName());
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "eh oy wtf", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, TuVung_MauCau_Activity.class);
                intent.putExtra("topic",arrayList.get(position).getTopicName());
                context.startActivity(intent);
            }
        });


        return convertView;
    }
}
