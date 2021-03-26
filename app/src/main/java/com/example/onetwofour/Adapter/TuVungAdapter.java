package com.example.onetwofour.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.onetwofour.Model.TuVung;
import com.example.onetwofour.R;

import java.util.ArrayList;
import java.util.Locale;

public class TuVungAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<TuVung> arrayList;
    TextToSpeech textToSpeech;
    public TuVungAdapter(Context context, int layout, ArrayList<TuVung> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        ImageView img,img_sound;
        TextView tv_desc,tv_word;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            viewHolder.img = convertView.findViewById(R.id.img_tuvung);
            viewHolder.tv_desc = convertView.findViewById(R.id.tv_desc_tuvung);
            viewHolder.tv_word = convertView.findViewById(R.id.tv_word_tuvung);
            viewHolder.img_sound = convertView.findViewById(R.id.img_sound_tuvung);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        byte[] Hinh = arrayList.get(position).getImage();
        Bitmap bm_hinh = BitmapFactory.decodeByteArray(Hinh,0,Hinh.length);
        viewHolder.img.setImageBitmap(bm_hinh);

        viewHolder.tv_desc.setText(arrayList.get(position).getDesc());
        viewHolder.tv_word.setText(arrayList.get(position).getWord());


        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        viewHolder.img_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = arrayList.get(position).getWord();
                int speech = textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        return convertView;
    }
}
