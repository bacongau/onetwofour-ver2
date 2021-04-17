package com.example.onetwofour.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.onetwofour.Adapter.DeckAdapter;
import com.example.onetwofour.Database.DataBase;
import com.example.onetwofour.Model.Deck;
import com.example.onetwofour.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EasyModeActivity extends AppCompatActivity {
    String DATABASE_NAME = "trochoi2 db.db";
    SQLiteDatabase database;

    RecyclerView rv;
    ArrayList<Deck> arrayList;
    DeckAdapter adapter;
    FloatingActionButton fab;

    int click = 0;
    Deck deck1;
    Deck deck2;
    int dem = 0;
    int process = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_mode);

        anhxa();
        laydulieu();

        adapter.setOnItemClickListener(new DeckAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (click < 2) {
                    if (dem == 0) {
                        dem++;
                        deck1 = arrayList.get(position);
                        if (deck1.getStatus() == 0) {
                            click++;
                            deck1.setStatus(1);
                            adapter.notifyDataSetChanged();
                        }
                    } else if (dem == 1) {
                        dem--;
                        deck2 = arrayList.get(position);
                        if (deck2.getStatus() == 0) {
                            click++;
                            deck2.setStatus(1);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    click = 0;
                }


                if (click == 2) {
                    rv.setClickable(false);
                    CountDownTimer countDownTimer = new CountDownTimer(400, 400) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            if (deck1.getId() == deck2.getId()) {
                                click = 0;
                                process++;
                                deck1.setStatus(2);
                                deck2.setStatus(2);
                                adapter.notifyDataSetChanged();

                                if (process == arrayList.size() / 2) {
                                    Toast.makeText(EasyModeActivity.this, "Chúc mừng bạn đã hoàn thành trò chơi !!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                click = 0;
                                deck1.setStatus(0);
                                deck2.setStatus(0);
                                adapter.notifyDataSetChanged();
                                deck1 = new Deck();
                                deck2 = new Deck();
                            }

                            rv.setClickable(true);
                        }
                    };
                    countDownTimer.start();
                }

                Log.e("click:", "click = " + click);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EasyModeActivity.this);
                builder1.setTitle("Chơi lại từ đầu");
                builder1.setMessage("Bạn muốn chơi từ đầu ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                for (int i=0;i<arrayList.size();i++){
                                    arrayList.get(i).setStatus(0);
                                }
                                adapter.notifyDataSetChanged();
                                click = 0;
                                dem = 0;
                                process = 0;
                                deck1 = new Deck();
                                deck2 = new Deck();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Không",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.setCanceledOnTouchOutside(false);
                alert11.show();
            }
        });
    }

    private void laydulieu() {
        database = DataBase.initDatabase(EasyModeActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM trochoi2 WHERE id < 7", null);
        arrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            byte[] imgword = cursor.getBlob(1);
            byte[] imgavatar = cursor.getBlob(2);
            byte[] imgfacedown = cursor.getBlob(3);
            int status = cursor.getInt(4);
            arrayList.add(new Deck(id, imgword, imgavatar, imgfacedown, status));
        }
        adapter.notifyDataSetChanged();
    }

    private void anhxa() {
        fab = findViewById(R.id.fab_memory_game);
        deck1 = new Deck();
        deck2 = new Deck();
        rv = findViewById(R.id.rv_deck);
        rv.setHasFixedSize(true);

        arrayList = new ArrayList<>();
        adapter = new DeckAdapter(arrayList);

        // setup cho recycleview
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rv.setLayoutManager(gridLayoutManager);

        rv.setAdapter(adapter);

        rv.addItemDecoration(new DividerItemDecoration(EasyModeActivity.this,
                DividerItemDecoration.VERTICAL));

    }
}