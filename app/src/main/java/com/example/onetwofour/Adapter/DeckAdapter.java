package com.example.onetwofour.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetwofour.Model.Deck;
import com.example.onetwofour.R;

import java.util.ArrayList;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder>{
    private ArrayList<Deck> arrayList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public DeckAdapter(ArrayList<Deck> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public DeckAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deck, parent, false);

        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int id = arrayList.get(position).getId();
        ///////////////
        byte[] imgword = arrayList.get(position).getImgword();
        Bitmap bmImgword = null;
        if (imgword != null) {
            bmImgword = BitmapFactory.decodeByteArray(imgword, 0, imgword.length);
        }

        ///////////////
        byte[] imgavatar = arrayList.get(position).getImgavatar();
        Bitmap bmImgavatar = null;
        if (imgavatar != null) {
            bmImgavatar = BitmapFactory.decodeByteArray(imgavatar, 0, imgavatar.length);
        }

        ///////////////
        byte[] imgfacedown = arrayList.get(position).getImgfacedown();
        Bitmap bmImgfacedown = null;
        if (imgfacedown != null) {
            bmImgfacedown = BitmapFactory.decodeByteArray(imgfacedown, 0, imgfacedown.length);
        }

        int status = arrayList.get(position).getStatus();

        holder.setData(id, bmImgword, bmImgavatar, bmImgfacedown, status, position);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            img = itemView.findViewById(R.id.img_deck);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arrayList.get(getPosition()).getStatus() == 2) {

                    } else {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemClick(position);
                            }
                        }
                    }

                }
            });
        }

        public void setData(int id, Bitmap bmImgword, Bitmap bmImgavatar, Bitmap bmImgfacedown, int status, int position) {
            if (status == 0) {
                img.setImageBitmap(bmImgfacedown);
            }
            if (bmImgword != null && status == 1) {
                img.setImageBitmap(bmImgword);
            }
            if (bmImgavatar != null && status == 1) {
                img.setImageBitmap(bmImgavatar);
            }
            if (bmImgword != null && status == 2) {
                img.setImageBitmap(bmImgword);
            }
            if (bmImgavatar != null && status == 2) {
                img.setImageBitmap(bmImgavatar);
            }
        }
    }
}
