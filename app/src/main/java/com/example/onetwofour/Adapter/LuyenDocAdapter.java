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
import com.example.onetwofour.Activities.BaiNgheActivity;
import com.example.onetwofour.Activities.LuyenDocActivity;
import com.example.onetwofour.Activities.TuVung_MauCau_Activity;
import com.example.onetwofour.Model.BaiDoc;
import com.example.onetwofour.Model.TuVung;
import com.example.onetwofour.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class LuyenDocAdapter extends RecyclerView.Adapter<LuyenDocAdapter.ViewHolder> {
    private ArrayList<BaiDoc> arrayList;
    private OnItemClickListener mListener;
    private OnItemLongClickListener mLongListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener mlistener2){
        mLongListener = mlistener2;
    }

    public LuyenDocAdapter(ArrayList<BaiDoc> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public LuyenDocAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baidoc, parent, false);

        return new ViewHolder(view,mListener,mLongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ten = arrayList.get(position).getTen();
        String hinh = arrayList.get(position).getHinh();

        holder.setData(ten, hinh, position);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;
        Button btn;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener, OnItemLongClickListener longClickListener) {
            super(itemView);

            img = itemView.findViewById(R.id.img_baidoc);
            tv = itemView.findViewById(R.id.tv_tenbaidoc);
            btn = itemView.findViewById(R.id.btn_xembaidoc);


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
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longClickListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            longClickListener.onItemLongClicked(position);
                        }
                    }
                    return false;
                }
            });
        }

        public void setData(String word, String bm, int position) {
            Picasso.get().load(bm).placeholder(R.drawable.school).into(img);
            tv.setText(word);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BaiDocActivity.class);
                    intent.putExtra("topicbaidoc",arrayList.get(position).getTen());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }


}
