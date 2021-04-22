package com.example.onetwofour.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetwofour.Activities.NguPhapActivity;
import com.example.onetwofour.Activities.TuVung_MauCau_Activity;
import com.example.onetwofour.Model.NguPhap;
import com.example.onetwofour.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class
NguPhapAdapter extends RecyclerView.Adapter<NguPhapAdapter.ViewHolder> {
    private ArrayList<NguPhap> arrayList;
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


    public NguPhapAdapter(ArrayList<NguPhap> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public NguPhapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_nguphap, parent, false);

        return new ViewHolder(view,mListener,mLongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ten = arrayList.get(position).getTopicName();
        String hinh = arrayList.get(position).getHinh();



        holder.setData(ten, hinh, position);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tv;
        Button button;
        CardView cardView;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener,OnItemLongClickListener longClickListener) {
            super(itemView);

            img = itemView.findViewById(R.id.img_topic_nguphap);
            tv = itemView.findViewById(R.id.tv_topic_nguphap);
            button = itemView.findViewById(R.id.btn_topic_nguphap);

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

        public void setData(String ten, String hinh, int position) {
            Picasso.get().load(hinh).placeholder(R.drawable.school).into(img);
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
