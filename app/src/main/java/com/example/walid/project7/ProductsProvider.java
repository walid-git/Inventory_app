package com.example.walid.project7;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.walid.project7.ProductsContract;
public class ProductsProvider extends ContentProvider {

    public static final int PRODUCTS = 1;
    public static final int PRODUCTS_ID = 2;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(ProductsContract.CONTENT_AUTHORITY,ProductsContract.PATH_PRODUCTS,PRODUCTS);
        matcher.addURI(ProductsContract.CONTENT_AUTHORITY, ProductsContract.PATH_PRODUCTS + "/#", PRODUCTS_ID);
    }

    MyDatabase myDatabase;

    @Override
    public boolean onCreate() {
        myDatabase = new MyDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        int match = matcher.match(uri);
        if (match == PRODUCTS || match == PRODUCTS_ID) {
            if (match == PRODUCTS_ID) {
                selection = ProductsContract.ProductsEntry.COLUMN_ID + " = ?";
                selectionArgs = new String[]{"" + ContentUris.parseId(uri)};
            }
            cursor = db.query(ProductsContract.ProductsEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                    );
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
            return cursor;
        }
        else {
            throw new IllegalArgumentException("Invalid Uri ");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)) {
            case PRODUCTS :
                return ProductsContract.ProductsEntry.CONTENT_MIME_TYPE_DIR;
            case PRODUCTS_ID :
                return ProductsContract.ProductsEntry.CONTENT_MIME_TYPE_ITEM;
            default:
                return null;
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (matcher.match(uri) == PRODUCTS) {
            Integer price = values.getAsInteger(ProductsContract.ProductsEntry.COLUMN_PRICE);
            Integer quantity = values.getAsInteger(ProductsContract.ProductsEntry.COLUMN_QUANTITY);
            boolean isValid = values.getAsString(ProductsContract.ProductsEntry.COLUMN_NAME)!=null;
            isValid &= ((price != null) && price >= 0);
            isValid &= ((quantity != null) && quantity >= 0);
            if (isValid) {
                SQLiteDatabase db = myDatabase.getWritableDatabase();
                long id = db.insert(ProductsContract.ProductsEntry.TABLE_NAME, null, values);
                if (id != -1) {
                    getContext().getContentResolver().notifyChange(uri,null);
                    return ContentUris.withAppendedId(uri, id);
                }
                else{
                    Log.e("walid","Error inserting element at "+uri);
                    return null;
                }

            }
            else
                throw new IllegalArgumentException("Invalid data, cannot be inserted");
        }
        else throw new IllegalArgumentException("Invalid Uri");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = matcher.match(uri);
        int deleted = 0;
        if (match == PRODUCTS) {
            deleted = myDatabase.getWritableDatabase().delete(ProductsContract.ProductsEntry.TABLE_NAME, selection, selectionArgs);
        } else if (match == PRODUCTS_ID) {
            selection = ProductsContract.ProductsEntry.COLUMN_ID + " = ?";
            selectionArgs = new String[]{"" + ContentUris.parseId(uri)};
            deleted = myDatabase.getWritableDatabase().delete(ProductsContract.ProductsEntry.TABLE_NAME, selection, selectionArgs);
        }
        if (deleted>0)
            getContext().getContentResolver().notifyChange(uri,null);
        return deleted;
    }
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = matcher.match(uri);

        if (match == PRODUCTS || match == PRODUCTS_ID) {
            boolean isValid = values.size()>0;

            if (values.containsKey(ProductsContract.ProductsEntry.COLUMN_NAME)) {
                isValid &= values.getAsString(ProductsContract.ProductsEntry.COLUMN_NAME)!=null ;
            }

            if (values.containsKey(ProductsContract.ProductsEntry.COLUMN_PRICE)) {
                Integer price = values.getAsInteger(ProductsContract.ProductsEntry.COLUMN_PRICE);
                isValid &= ((price != null) && price >= 0);
            }

            if (values.containsKey(ProductsContract.ProductsEntry.COLUMN_QUANTITY)) {
                Integer quantity = values.getAsInteger(ProductsContract.ProductsEntry.COLUMN_QUANTITY);
                isValid &= ((quantity != null) && quantity >= 0);
            }

            if(!isValid)
                throw new IllegalArgumentException("Invalid data, cannot be inserted");

            if (match == PRODUCTS_ID) {
                selection = ProductsContract.ProductsEntry.COLUMN_ID + " = ?";
                selectionArgs = new String[]{"" + ContentUris.parseId(uri)};
            }
            getContext().getContentResolver().notifyChange(uri,null);
            return myDatabase.getWritableDatabase().update(ProductsContract.ProductsEntry.TABLE_NAME, values, selection, selectionArgs);
        } else {
            throw new IllegalArgumentException("Invalid Uri");
        }
    }
}
