package com.example.qimou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class MainActivity extends AppCompatActivity {
    private Context context=this;
    private MyJdbc jd;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jd=new MyJdbc(context,"user",null,1);
        db=jd.getWritableDatabase();
    }
}