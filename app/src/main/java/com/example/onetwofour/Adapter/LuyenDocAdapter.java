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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetwofour.Activities.BaiDocActivity;
import com.example.onetwofour.Activities.TuVung_MauCau_Activity;
import com.example.onetwofour.Model.BaiDoc;
import com.example.onetwofour.Model.TuVung;
import com.example.onetwofour.R;

import java.util.ArrayList;
import java.util.Locale;

public class LuyenDocAdapter extends RecyclerView.Adapter<LuyenDocAdapter.ViewHolder> {
    private ArrayList<BaiDoc> arrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public LuyenDocAdapter(ArrayList<BaiDoc> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public LuyenDocAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baidoc, parent, false);

        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ten = arrayList.get(position).getTenbaidoc();
        byte[] hinh = arrayList.get(position).getHinh();
        Bitmap bm = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);

        holder.setData(ten, bm, position);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            img = itemView.findViewById(R.id.img_baidoc);
            tv = itemView.findViewById(R.id.tv_tenbaidoc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void setData(String word, Bitmap bm, int position) {
            img.setImageBitmap(bm);
            tv.setText(word);
        }
    }


}
