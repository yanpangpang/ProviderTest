package com.aiyipai.providertest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MyProvider extends ContentProvider {

    public static UriMatcher uriMatcher;
    public static final String PREFIX = "content://";
    public static final String AUTHORITY = "com.aiyipai.providertest";
    public static final int CONTACTS_SET = 1;
    public static final int CONTACTS_ITEM= 2;
    public MySqliteOpenHelper sqliteOpenHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"/Contacts",CONTACTS_SET);
        uriMatcher.addURI(AUTHORITY,"/Contacts/#",CONTACTS_ITEM);
    }

    @Override
    public boolean onCreate(){
        sqliteOpenHelper = new MySqliteOpenHelper(getContext(),"test",null,1);
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        Uri returnUri = null;
        switch(uriMatcher.match(uri)){
            case CONTACTS_SET:
                break;
            case CONTACTS_ITEM:
                SQLiteDatabase db = sqliteOpenHelper.getWritableDatabase();
                String number= uri.getPathSegments().get(1);
                returnUri = Uri.parse(PREFIX+AUTHORITY+"/Contacts/"+number);
                db.insert("Contacts",null,values);
                Log.d("MyProvider","insert succeed");
                break;
            default:
                break;
        }
        return returnUri;
    }

    @Override
    public Cursor query(Uri uri,String[] projection,String selection,String[] selectionArgs,String sortOrder){
        Cursor cursor = null;
        String number = uri.getPathSegments().get(1);
        SQLiteDatabase db = sqliteOpenHelper.getReadableDatabase();
        switch(uriMatcher.match(uri)){
            case CONTACTS_SET:
                cursor = db.query("Contacts",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CONTACTS_ITEM:
                cursor = db.query("Contacts",projection,"number = ?",new String[]{number},null,null,null);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri,ContentValues values,String selection,String[] selectionArgs){
        return 0;
    }

    @Override
    public int delete(Uri uri,String selection,String[] selectionArgs){
        return 0;
    }

    @Override
    public String getType(Uri uri){
        return null;
    }
}
