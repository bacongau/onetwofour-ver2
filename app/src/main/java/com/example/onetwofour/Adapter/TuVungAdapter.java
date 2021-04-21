package com.example.onetwofour.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetwofour.Model.TuVung;
import com.example.onetwofour.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TuVungAdapter extends RecyclerView.Adapter<TuVungAdapter.ViewHolder> {
    private ArrayList<TuVung> arrayList;
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

    public TuVungAdapter(ArrayList<TuVung> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public TuVungAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tuvung, parent, false);

        return new ViewHolder(view,mListener,mLongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String word = arrayList.get(position).getWord();
        String sub = arrayList.get(position).getDesc();
        String hinh = arrayList.get(position).getLinkhinh();

        holder.setData(word, hinh, sub, position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv_desc, tv_word;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener, OnItemLongClickListener longClickListener) {
            super(itemView);

            img = itemView.findViewById(R.id.img_tuvung);
            tv_word = itemView.findViewById(R.id.tv_word_tuvung);
            tv_desc = itemView.findViewById(R.id.tv_desc_tuvung);

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

        public void setData(String word, String bm, String sub, int position) {
            Picasso.get().load(bm).placeholder(R.drawable.cards).into(img);
            tv_word.setText(word);
            tv_desc.setText(sub);
        }
    }




}
