package com.example.admin.lecture10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 4/13/2017.
 */

public class OwnDBHandler extends SQLiteOpenHelper {
    private Context mDBContext;

    //database name
    private static final String DATABASE_NAME="season_record.db";
    private static final int DATABASE_VERSION=1;

    private static final String SEASON_TABLE_NAME="season_info";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_SEASON_NAME="season_name";
    private static final String COLUMN_SEASON_RATE="season_rate";
    //create season table
    private static final String CREATE_TABLE="CREATE TABLE "+ SEASON_TABLE_NAME + "(" +
                                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                                COLUMN_SEASON_NAME + " VARCHAR(255) ," +
                                COLUMN_SEASON_RATE + " FLOAT);";
    //delete the table and used new table
    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+SEASON_TABLE_NAME;


    public OwnDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mDBContext=context;
        ToastMessage.message(mDBContext, "constructor created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        }catch (SQLException ex){
            ToastMessage.message(mDBContext, "Error: "+ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (SQLException ex){
            ToastMessage.message(mDBContext, "Error: "+ex);
        }
    }

    public long insertSeasonData(String seasonName, Float seasonRate){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        //content values for  insertion, update and replacing data from table
        ContentValues values=new ContentValues();
        values.put(COLUMN_SEASON_NAME,seasonName);
        values.put(COLUMN_SEASON_RATE, seasonRate);
        long id=sqLiteDatabase.insert(SEASON_TABLE_NAME, null, values);
        return id;
    }

    public String printDatabase(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();

        String[] columns={COLUMN_ID, COLUMN_SEASON_NAME, COLUMN_SEASON_RATE};
        Cursor cursor=sqLiteDatabase.query(SEASON_TABLE_NAME,columns, null,null, null, null, null);
        //buffer for storing data
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext()){
            int id_index=cursor.getColumnIndex(COLUMN_ID);
            int id_value=cursor.getInt(id_index);

            int name_index=cursor.getColumnIndex(COLUMN_SEASON_NAME);
            String name_value=cursor.getString(name_index);

            int rate_index=cursor.getColumnIndex(COLUMN_SEASON_RATE);
            Float rate_value=cursor.getFloat(rate_index);

            buffer.append(id_value+" "+name_value+" "+rate_value+"\n");
        }
        return buffer.toString();
    }

    public String searchData(String seasonName){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();

        String[] columns={COLUMN_ID, COLUMN_SEASON_NAME, COLUMN_SEASON_RATE};
        Cursor cursor=sqLiteDatabase.query(SEASON_TABLE_NAME,columns,COLUMN_SEASON_NAME+"='"+ seasonName +"'",null, null, null, null);
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext()){
            int id_index=cursor.getColumnIndex(COLUMN_ID);
            int id_value=cursor.getInt(id_index);

            int name_index=cursor.getColumnIndex(COLUMN_SEASON_NAME);
            String name_value=cursor.getString(name_index);

            int rate_index=cursor.getColumnIndex(COLUMN_SEASON_RATE);
            Float rate_value=cursor.getFloat(rate_index);

            buffer.append(id_value+" "+name_value+" "+rate_value+"\n");
        }
        return buffer.toString();
    }

    public int deleteData(String seasonName){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String[] whereArgs={seasonName};
        int count=sqLiteDatabase.delete(SEASON_TABLE_NAME, COLUMN_SEASON_NAME +"=?", whereArgs);
        return count;
    }

    public int updateData(String oldName, String newName, Float seasonRate){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_SEASON_NAME, newName);
        values.put(COLUMN_SEASON_RATE, seasonRate);

        String[] whereArgs={oldName};
        int count=sqLiteDatabase.update(SEASON_TABLE_NAME, values, COLUMN_SEASON_NAME+"=?", whereArgs);
        return count;
    }

}
