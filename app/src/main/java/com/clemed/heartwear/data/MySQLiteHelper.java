package com.clemed.heartwear.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Nic on 12/3/2014.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_CONTACTS = "table_contact";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_GCM_ID = "gcm_id";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_NAME = "name";

    private static final String DATABASE_NAME = "contactinfo.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_CONTACTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_GCM_ID
            + " text not null, " + COLUMN_PHONE_NUMBER
            + " text not null, " + COLUMN_NAME
            + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(db);
    }

}
