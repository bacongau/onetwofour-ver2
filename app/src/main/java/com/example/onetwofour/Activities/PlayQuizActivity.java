package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.CauHoi;
import com.example.onetwofour.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PlayQuizActivity extends AppCompatActivity {
    String DATABASE_NAME = "cauhoi db.db";
    SQLiteDatabase database;

    ArrayList<CauHoi> cauHoiArrayList;
    CauHoi cauHoi;

    TextView tv_causo, tv_somang, tv_cauhoi;
    Button btn_chotdapan, btn_1, btn_2, btn_3, btn_4;

    int picker = 0;
    int cauhoiso = 1;
    int somang = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quiz);

        anhxa();
        laycauhoi();
        setupBatDau();

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_1.setBackgroundResource(R.drawable.custom_button_2);
                btn_2.setBackgroundResource(R.drawable.custom_button);
                btn_3.setBackgroundResource(R.drawable.custom_button);
                btn_4.setBackgroundResource(R.drawable.custom_button);
                picker = 1;
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_2.setBackgroundResource(R.drawable.custom_button_2);
                btn_1.setBackgroundResource(R.drawable.custom_button);
                btn_3.setBackgroundResource(R.drawable.custom_button);
                btn_4.setBackgroundResource(R.drawable.custom_button);
                picker = 2;
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_3.setBackgroundResource(R.drawable.custom_button_2);
                btn_2.setBackgroundResource(R.drawable.custom_button);
                btn_1.setBackgroundResource(R.drawable.custom_button);
                btn_4.setBackgroundResource(R.drawable.custom_button);
                picker = 3;
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_4.setBackgroundResource(R.drawable.custom_button_2);
                btn_2.setBackgroundResource(R.drawable.custom_button);
                btn_3.setBackgroundResource(R.drawable.custom_button);
                btn_1.setBackgroundResource(R.drawable.custom_button);
                picker = 4;
            }
        });

        btn_chotdapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (picker) {
                    case 1: {
                        if (btn_1.getText().equals(cauHoi.getDapandung())) {
                            Toast.makeText(PlayQuizActivity.this, "Chính xác !!!", Toast.LENGTH_SHORT).show();
                            cauhoiso++;
                            tv_causo.setText("Câu số: " + cauhoiso);
                            resetChucNang();
                            doiCauHoi();
                        } else {
                            Toast.makeText(PlayQuizActivity.this, "Sai rồi :((\n"+"Hãy thử lại", Toast.LENGTH_SHORT).show();
                            somang--;
                            tv_somang.setText("Số mạng:" + somang + "/" + 3);
                            if (somang == 0) {
                                hienDialog();
                            }
                        }
                        break;
                    }
                    case 2: {
                        if (btn_2.getText().equals(cauHoi.getDapandung())) {
                            Toast.makeText(PlayQuizActivity.this, "Chính xác !!!", Toast.LENGTH_SHORT).show();
                            cauhoiso++;
                            tv_causo.setText("Câu số: " + cauhoiso);
                            resetChucNang();
                            doiCauHoi();
                        } else {
                            Toast.makeText(PlayQuizActivity.this, "Sai rồi :((\n"+"Hãy thử lại", Toast.LENGTH_SHORT).show();
                            somang--;
                            tv_somang.setText("Số mạng:" + somang + "/" + 3);
                            if (somang == 0) {
                                hienDialog();
                            }
                        }
                        break;
                    }
                    case 3: {
                        if (btn_3.getText().equals(cauHoi.getDapandung())) {
                            Toast.makeText(PlayQuizActivity.this, "Chính xác !!!", Toast.LENGTH_SHORT).show();
                            cauhoiso++;
                            tv_causo.setText("Câu số: " + cauhoiso);
                            resetChucNang();
                            doiCauHoi();
                        } else {
                            Toast.makeText(PlayQuizActivity.this, "Sai rồi :((\n"+"Hãy thử lại", Toast.LENGTH_SHORT).show();
                            somang--;
                            tv_somang.setText("Số mạng:" + somang + "/" + 3);
                            if (somang == 0) {
                                hienDialog();
                            }
                        }
                        break;
                    }
                    case 4: {
                        if (btn_4.getText().equals(cauHoi.getDapandung())) {
                            Toast.makeText(PlayQuizActivity.this, "Chính xác !!!", Toast.LENGTH_SHORT).show();
                            cauhoiso++;
                            tv_causo.setText("Câu số: " + cauhoiso);
                            resetChucNang();
                            doiCauHoi();
                        } else {
                            Toast.makeText(PlayQuizActivity.this, "Sai rồi :((\n"+"Hãy thử lại", Toast.LENGTH_SHORT).show();
                            somang--;
                            tv_somang.setText("Số mạng:" + somang + "/" + 3);
                            if (somang == 0) {
                                hienDialog();
                            }
                        }
                        break;
                    }
                    default:{
                        Toast.makeText(PlayQuizActivity.this, "Hãy chọn 1 đáp án", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
    }

    private void resetChucNang() {
        btn_4.setBackgroundResource(R.drawable.custom_button);
        btn_2.setBackgroundResource(R.drawable.custom_button);
        btn_3.setBackgroundResource(R.drawable.custom_button);
        btn_1.setBackgroundResource(R.drawable.custom_button);
        picker = 0;
    }

    private void hienDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PlayQuizActivity.this);
        builder1.setTitle("Bạn đã hết số lần chơi");
        builder1.setMessage("Bạn có muốn chơi lại không?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        choilaitudau();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Không",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(PlayQuizActivity.this, GameQuizActivity.class));
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void choilaitudau() {
        laycauhoi();
        setupBatDau();
        cauhoiso = 1;
        tv_causo.setText("Câu số: " + cauhoiso);
        somang = 3;
        tv_somang.setText("Số mạng:" + somang + "/" + 3);
    }

    private void doiCauHoi() {
        // xoa cau hoi vua roi trong mang
        cauHoiArrayList.remove(cauHoi);
        // lay 1 cau hoi random khac
        Random generator = new Random();
        int max = cauHoiArrayList.size() - 1;
        int min = 0;
        int value = generator.nextInt((max - min) + 1) + min;
        cauHoi = cauHoiArrayList.get(value);

        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(cauHoi.getDapan1());
        stringArrayList.add(cauHoi.getDapan2());
        stringArrayList.add(cauHoi.getDapan3());
        stringArrayList.add(cauHoi.getDapandung());
        Collections.shuffle(stringArrayList);
        tv_cauhoi.setText(cauHoi.getCauhoi());
        btn_1.setText(stringArrayList.get(0));
        btn_2.setText(stringArrayList.get(1));
        btn_3.setText(stringArrayList.get(2));
        btn_4.setText(stringArrayList.get(3));
    }

    private void setupBatDau() {
        cauhoiso = 1;
        Random generator = new Random();
        int max = cauHoiArrayList.size() - 1;
        int min = 0;
        int value = generator.nextInt((max - min) + 1) + min;
        cauHoi = cauHoiArrayList.get(value);

        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(cauHoi.getDapan1());
        stringArrayList.add(cauHoi.getDapan2());
        stringArrayList.add(cauHoi.getDapan3());
        stringArrayList.add(cauHoi.getDapandung());
        Collections.shuffle(stringArrayList);
        tv_cauhoi.setText(cauHoi.getCauhoi());
        btn_1.setText(stringArrayList.get(0));
        btn_2.setText(stringArrayList.get(1));
        btn_3.setText(stringArrayList.get(2));
        btn_4.setText(stringArrayList.get(3));
    }

    private void laycauhoi() {
        database = DataBase.initDatabase(PlayQuizActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Cauhoi", null);
        cauHoiArrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String cauhoi = cursor.getString(1);
            String dapan1 = cursor.getString(2);
            String dapan2 = cursor.getString(3);
            String dapan3 = cursor.getString(4);
            String dapandung = cursor.getString(5);
            cauHoiArrayList.add(new CauHoi(id, cauhoi, dapan1, dapan2, dapan3, dapandung));
        }
    }

    private void anhxa() {
        tv_cauhoi = findViewById(R.id.tv_cauhoi);
        tv_somang = findViewById(R.id.tv_somang);
        tv_causo = findViewById(R.id.tv_causo);
        btn_chotdapan = findViewById(R.id.btn_chotdapan);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);

        cauHoiArrayList = new ArrayList<>();
        cauHoi = new CauHoi();
    }

}