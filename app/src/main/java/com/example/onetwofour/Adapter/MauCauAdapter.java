package com.example.onetwofour.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetwofour.Model.MauCau;
import com.example.onetwofour.Model.TuVung;
import com.example.onetwofour.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MauCauAdapter extends RecyclerView.Adapter<MauCauAdapter.ViewHolder> {
    private ArrayList<MauCau> arrayList;
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

    public MauCauAdapter(ArrayList<MauCau> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MauCauAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maucau, parent, false);

        return new ViewHolder(view,mListener,mLongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String engsub = arrayList.get(position).getEng();
        String vietsub = arrayList.get(position).getViet();

        holder.setData(engsub, vietsub, position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_eng,tv_viet;

        public ViewHolder(@NonNull View itemView, MauCauAdapter.OnItemClickListener listener, OnItemLongClickListener longClickListener) {
            super(itemView);

            tv_eng = itemView.findViewById(R.id.tv_maucau_eng);
            tv_viet = itemView.findViewById(R.id.tv_maucau_viet);

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

        public void setData(String engsub, String vietsub, int position) {
            tv_eng.setText(engsub);
            tv_viet.setText(vietsub);
        }
    }



}
