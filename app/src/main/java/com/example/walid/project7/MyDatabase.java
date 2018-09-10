package com.example.walid.project7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "inventory";

    public static final int DB_VERSION = 1;

    public MyDatabase(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+DbContract.ProductsEntry.TABLE_NAME+
        "("+DbContract.ProductsEntry.COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
               DbContract.ProductsEntry.COLUMN_NAME +" TEXT NOT NULL," +
                DbContract.ProductsEntry.COLUMN_PRICE +" INTEGER NOT NULL DEFAULT 0," +
                DbContract.ProductsEntry.COLUMN_QUANTITY +" INTEGER NOT NULL DEFAULT 0," +
                DbContract.ProductsEntry.COLUMN_SUPPLIER_NAME +" TEXT," +
                DbContract.ProductsEntry.COLUMN_SUPPLIER_PHONE +" TEXT" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table "+DbContract.ProductsEntry.TABLE_NAME+";");
    }
}
