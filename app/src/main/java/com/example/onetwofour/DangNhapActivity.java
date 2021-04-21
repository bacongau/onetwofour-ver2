package com.example.onetwofour;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DangNhapActivity extends AppCompatActivity {
    Button btn_batdau, btn_dangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        anhxa();

        btn_batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this,MainActivity.class);
                intent.putExtra("data","abc");
                startActivity(intent);
            }
        });

        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(DangNhapActivity.this);
                dialog.setContentView(R.layout.dialog_dangnhap);

                EditText edt_tendangnhap = dialog.findViewById(R.id.edt_tendangnhap);
                EditText edt_matkhau = dialog.findViewById(R.id.edt_matkhau);
                Button btn_ok = dialog.findViewById(R.id.btn_dialogthemchude_ok);
                Button btn_huy = dialog.findViewById(R.id.btn_dialogthemchude_huy);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a = edt_tendangnhap.getText().toString();
                        String b = edt_matkhau.getText().toString();
                        if (a.isEmpty() || b.isEmpty()){
                            edt_tendangnhap.setError("Không để trống trường này");
                            edt_matkhau.setError("Không để trống trường này");
                        }else if (a.equals("admin") && b.equals("123")){
                            Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                            intent.putExtra("data","admin");
                            startActivity(intent);
                            dialog.dismiss();
                        }else {
                            Toast.makeText(DangNhapActivity.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }


    private void anhxa() {
        btn_batdau = findViewById(R.id.btn_batdaungay);
        btn_dangnhap = findViewById(R.id.btn_dangnhap);
    }
}