package com.clemed.heartwear.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nic on 12/3/2014.
 */
public class ContactDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_GCM_ID, MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_PHONE_NUMBER };
    private boolean isOpen;


    private static ContactDataSource mInstance;

    private ContactDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        isOpen = false;
    }

    public static ContactDataSource getmInstance(Context c){
        if(mInstance == null){
            mInstance = new ContactDataSource(c);
        }
        return mInstance;
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        isOpen = true;
    }

    public boolean isOpen(){return isOpen;}

    public void close(){
        dbHelper.close();
        isOpen = false;
    }

    /**
     * This creates a note, then returns the newly created note in order to update
     * the UI.
     *
     */
    public void setContact(EmergencyContact contact) throws NullPointerException {

        if(contact == null){
             throw new NullPointerException("Contact was not set");
        }

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_GCM_ID, contact.getGcmId());
        values.put(MySQLiteHelper.COLUMN_NAME, contact.getName());
        values.put(MySQLiteHelper.COLUMN_PHONE_NUMBER, contact.getPhoneNumber());

        if(contactExists()){

            String where = MySQLiteHelper.COLUMN_PHONE_NUMBER + "=" + contact.getPhoneNumber();
            database.update(MySQLiteHelper.TABLE_CONTACTS, values, where, null);

        }else{
            database.insert(MySQLiteHelper.TABLE_CONTACTS, null, values);
        }

    }

    public EmergencyContact getContact(){

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CONTACTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        EmergencyContact contact = cursorToContact(cursor);

        // make sure to close the cursor
        cursor.close();
        return contact;
    }

    private boolean contactExists(){

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CONTACTS,
                allColumns, null, null, null, null, null);

        boolean result = cursor.moveToFirst();

        cursor.close();

        return result;
    }

    private EmergencyContact cursorToContact(Cursor cursor){
        return new EmergencyContact(
                cursor.getString( cursor.getColumnIndex(MySQLiteHelper.COLUMN_PHONE_NUMBER)),
                cursor.getString( cursor.getColumnIndex(MySQLiteHelper.COLUMN_GCM_ID) ),
                cursor.getString( cursor.getColumnIndex(MySQLiteHelper.COLUMN_NAME))
        );

    }

}