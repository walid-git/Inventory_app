package com.example.walid.project7;

import android.provider.BaseColumns;

public class DbContract {

    public DbContract() {
    }

    public class ProductsEntry implements BaseColumns {

        public static final String TABLE_NAME = "products";

        public static final String COLUMN_ID = _ID;

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_PRICE = "price";

        public static final String COLUMN_QUANTITY = "quantity";

        public static final String COLUMN_SUPPLIER_NAME = "supplier";

        public static final String COLUMN_SUPPLIER_PHONE = "supplier_phone";

    }

}
