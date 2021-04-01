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

import java.util.ArrayList;

import static com.example.onetwofour.R.drawable.academy;

public class LuyenNgheAdapter extends RecyclerView.Adapter<LuyenNgheAdapter.ViewHolder> {
    private ArrayList<BaiNghe> arrayList;

    public LuyenNgheAdapter(ArrayList<BaiNghe> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public LuyenNgheAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_luyennghe, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LuyenNgheAdapter.ViewHolder holder, int position) {

        byte[] hinh = arrayList.get(position).getHinh();
        Bitmap bm = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);

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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_topic_luyennghe);
            img_audio = itemView.findViewById(R.id.img_luyennghe);
            tv = itemView.findViewById(R.id.tv_topic_luyennghe);
        }

        public void setData(String ten, Bitmap bm, int position) {
            img.setImageBitmap(bm);
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