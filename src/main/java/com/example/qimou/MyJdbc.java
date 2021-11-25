package com.example.qimou;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyJdbc extends SQLiteOpenHelper {
    public MyJdbc(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表
        db.execSQL("create table if not exists login("+
                "name varchar,"+
//                "user varchar primary key,"+
                "user varchar,"+
                "sex varchar,"+
                "pass varchar,"+
                "photo varchar,"+
                "flag int)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
