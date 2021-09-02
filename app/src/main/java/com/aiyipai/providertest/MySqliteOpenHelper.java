package com.aiyipai.providertest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteOpenHelper extends SQLiteOpenHelper {
    private Context mContext;
    public static final String CREATE = "create table Contacts("
            +"number integer primary key,"
            +"address text);";
    public MySqliteOpenHelper(Context mContext, String dbName, SQLiteDatabase.CursorFactory factory,int version){
        super(mContext,dbName,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE);
        Log.d("onCreate","create table Contacts succeed");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        Log.d("onUpgrade","database has been upgraded");
    }
}
