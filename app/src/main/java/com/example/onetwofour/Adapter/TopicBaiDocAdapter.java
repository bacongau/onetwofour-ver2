package com.example.onetwofour.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onetwofour.Activities.BaiDocActivity;
import com.example.onetwofour.Activities.TuVung_MauCau_Activity;
import com.example.onetwofour.Model.BaiDoc;
import com.example.onetwofour.Model.TuVung;
import com.example.onetwofour.R;

import java.util.ArrayList;
import java.util.Locale;

public class TopicBaiDocAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<BaiDoc> arrayList;

    public TopicBaiDocAdapter(Context context, int layout, ArrayList<BaiDoc> arrayList) {
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

            viewHolder.img = convertView.findViewById(R.id.img_baidoc);
            viewHolder.btn = convertView.findViewById(R.id.btn_xembaidoc);
            viewHolder.tv = convertView.findViewById(R.id.tv_tenbaidoc);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        byte[] Hinh = arrayList.get(position).getHinh();
        Bitmap bm_hinh = BitmapFactory.decodeByteArray(Hinh,0,Hinh.length);
        viewHolder.img.setImageBitmap(bm_hinh);

        viewHolder.tv.setText(arrayList.get(position).getTenbaidoc());
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BaiDocActivity.class);
                intent.putExtra("topicbaidoc",arrayList.get(position).getTenbaidoc());
                context.startActivity(intent);
            }
        });


        return convertView;
    }
}
