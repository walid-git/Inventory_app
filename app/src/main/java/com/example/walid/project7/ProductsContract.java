package com.example.walid.project7;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ProductsContract {

    public static final String CONTENT_AUTHORITY = "com.example.walid.inventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_PRODUCTS = "products";

    public ProductsContract() {
    }

    public static class ProductsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        public static final String CONTENT_MIME_TYPE_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        public static final String CONTENT_MIME_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        public static final String TABLE_NAME = "products";

        public static final String COLUMN_ID = _ID;

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_PRICE = "price";

        public static final String COLUMN_QUANTITY = "quantity";

        public static final String COLUMN_SUPPLIER_NAME = "supplier";

        public static final String COLUMN_SUPPLIER_PHONE = "supplier_phone";

    }

}
