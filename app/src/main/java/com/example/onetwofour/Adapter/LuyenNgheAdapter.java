package com.example.onetwofour.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetwofour.Activities.BaiNgheActivity;
import com.example.onetwofour.Activities.TuVung_MauCau_Activity;
import com.example.onetwofour.Model.BaiNghe;
import com.example.onetwofour.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.onetwofour.R.drawable.academy;

public class LuyenNgheAdapter extends RecyclerView.Adapter<LuyenNgheAdapter.ViewHolder> {
    private ArrayList<BaiNghe> arrayList;
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

    public LuyenNgheAdapter(ArrayList<BaiNghe> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public LuyenNgheAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_luyennghe, parent, false);

        return new ViewHolder(view,mListener,mLongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String bm = arrayList.get(position).getHinh();

        String ten = arrayList.get(position).getTen();

        holder.setData(ten, bm, position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, img_audio;
        TextView tv;


        public ViewHolder(@NonNull View itemView, OnItemClickListener listener, OnItemLongClickListener longClickListener) {
            super(itemView);

            img = itemView.findViewById(R.id.img_topic_luyennghe);
            img_audio = itemView.findViewById(R.id.img_luyennghe);
            tv = itemView.findViewById(R.id.tv_topic_luyennghe);

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

        public void setData(String ten, String bm, int position) {
            Picasso.get().load(bm).placeholder(R.drawable.mp3player).into(img);


            tv.setText(ten);
            img_audio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BaiNgheActivity.class);
                    intent.putExtra("tenbainghe",arrayList.get(position).getTen());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
