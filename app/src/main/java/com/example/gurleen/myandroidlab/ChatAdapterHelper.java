package com.example.gurleen.myandroidlab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Geet on 11/20/2017.
 */

public class ChatAdapterHelper extends SQLiteOpenHelper {
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";
    public static final String DATABASE_NAME = "Messages";
    private static final int VERSION_NUM=1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("QueryCreateTable", "CREATE TABLE " + DATABASE_NAME
                +  "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_MESSAGE +" text);");
        db.execSQL( "CREATE TABLE " + DATABASE_NAME
                +  "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_MESSAGE +" text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion) {
        db.execSQL("Drop table if exists "+DATABASE_NAME);
        this.onCreate(db);
    }
    public ChatAdapterHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

} 

