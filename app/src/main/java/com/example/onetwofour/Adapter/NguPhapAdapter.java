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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetwofour.Activities.BaiNgheActivity;
import com.example.onetwofour.Activities.TuVung_MauCau_Activity;
import com.example.onetwofour.Model.BaiNghe;
import com.example.onetwofour.Model.TopicNguPhap;
import com.example.onetwofour.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class
NguPhapAdapter extends RecyclerView.Adapter<NguPhapAdapter.ViewHolder> {
    private ArrayList<TopicNguPhap> arrayList;

    public NguPhapAdapter(ArrayList<TopicNguPhap> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public NguPhapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_nguphap, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ten = arrayList.get(position).getTopicName();
        byte[] hinh = arrayList.get(position).getImg();
        Bitmap bm = BitmapFactory.decodeByteArray(hinh,0,hinh.length);

        holder.setData(ten, bm, position);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_topic_nguphap);
            tv = itemView.findViewById(R.id.tv_topic_nguphap);
            button = itemView.findViewById(R.id.btn_topic_nguphap);
        }

        public void setData(String ten, Bitmap bm, int position) {
            img.setImageBitmap(bm);
            tv.setText(ten);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),TuVung_MauCau_Activity.class);
                    intent.putExtra("topic",arrayList.get(position).getTopicName());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}
