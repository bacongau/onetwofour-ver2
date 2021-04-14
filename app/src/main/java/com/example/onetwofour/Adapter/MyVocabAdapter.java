package com.example.onetwofour.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetwofour.Model.MauCau;
import com.example.onetwofour.Model.MyVocab;
import com.example.onetwofour.R;

import java.util.ArrayList;

public class MyVocabAdapter extends RecyclerView.Adapter<MyVocabAdapter.ViewHolder>{
    private ArrayList<MyVocab> arrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public MyVocabAdapter(ArrayList<MyVocab> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyVocabAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_vocab, parent, false);

        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String keyword = arrayList.get(position).getKeyword();
        String mota = arrayList.get(position).getMota();

        holder.setData(keyword, mota, position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_keyword,tv_mota;

        public ViewHolder(@NonNull View itemView, MyVocabAdapter.OnItemClickListener listener) {
            super(itemView);

            tv_keyword = itemView.findViewById(R.id.tv_keyword);
            tv_mota = itemView.findViewById(R.id.tv_mota);

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

        public void setData(String keyword, String mota, int position) {
            tv_keyword.setText(keyword);
            tv_mota.setText(mota);
        }
    }
}
