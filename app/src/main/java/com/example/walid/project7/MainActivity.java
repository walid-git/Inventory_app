package com.example.walid.project7;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insertData("le fils du pauvre",10,120,"Edition la plume","021481515");
        insertData("les chemins qui montent",54,23,"Edition la plume","021481515");
        insertData("sahih muslim",65,800,"Dar al maarifa","021120000");
        insertData("Oxford dictionary",32,99,"Oxford library","0051120000");
        queryData();
    }

    public void insertData(String name, int price, int quantity, String supp, String phone) {
        MyDatabase myDatabase = new MyDatabase(this);
        SQLiteDatabase mDatabase = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.ProductsEntry.COLUMN_NAME,name);
        values.put(DbContract.ProductsEntry.COLUMN_PRICE,price);
        values.put(DbContract.ProductsEntry.COLUMN_QUANTITY,quantity);
        values.put(DbContract.ProductsEntry.COLUMN_SUPPLIER_NAME,supp);
        values.put(DbContract.ProductsEntry.COLUMN_SUPPLIER_PHONE, phone);
        mDatabase.insert(DbContract.ProductsEntry.TABLE_NAME, null, values);
        mDatabase.close();
    }


    public void queryData() {
        MyDatabase myDatabase = new MyDatabase(this);
        SQLiteDatabase mDatabase = myDatabase.getReadableDatabase();
        Cursor c = mDatabase.query(DbContract.ProductsEntry.TABLE_NAME, null, null, null, null, null, null);
        int idIndex = c.getColumnIndex(DbContract.ProductsEntry.COLUMN_ID);
        int nameIndex = c.getColumnIndex(DbContract.ProductsEntry.COLUMN_NAME);
        int priceIndex = c.getColumnIndex(DbContract.ProductsEntry.COLUMN_PRICE);
        int quantityIndex = c.getColumnIndex(DbContract.ProductsEntry.COLUMN_QUANTITY);
        int suppNameIndex = c.getColumnIndex(DbContract.ProductsEntry.COLUMN_SUPPLIER_NAME);
        int suppPhoneIndex = c.getColumnIndex(DbContract.ProductsEntry.COLUMN_SUPPLIER_PHONE);
        StringBuilder str = new StringBuilder();
        while (c.moveToNext()) {
           str.append("\nID: "+c.getInt(idIndex)+
                    "\nname: "+c.getString(nameIndex)+
                    "\nprice: "+c.getInt(priceIndex)+
                    "\nquantity: "+c.getInt(quantityIndex)+
                    "\nsupplier name: "+c.getString(suppNameIndex)+
                    "\nsupplier phone: "+c.getString(suppPhoneIndex)+
                    "\n---------------------------------------------------\n"
            );
        }
        Log.d("walidTag", "query result:\n" + str.toString());
        mDatabase.close();
        c.close();
    }
}
