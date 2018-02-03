package com.pilambda.findmerenew.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.pilambda.findmerenew.DataBase.DBConstants.DATABASENAME;
import static com.pilambda.findmerenew.DataBase.DBConstants.TABLE_PULSERAS;
import static com.pilambda.findmerenew.DataBase.DBConstants.TABLE_PULSERAS_Foto;
import static com.pilambda.findmerenew.DataBase.DBConstants.TABLE_PULSERAS_ID_;
import static com.pilambda.findmerenew.DataBase.DBConstants.TABLE_PULSERAS_NOMBRE;

/**
 * Created by qualtop on 29/01/18.
 */

public class AppDataBase {

    private static AppDataBase sInstance;
    private DBHelper mDBHelper;

    private AppDataBase(Context context){
        mDBHelper = new DBHelper(context);
    }



    public SQLiteDatabase getDB() {
        return mDBHelper.getWritableDatabase();
    }


    private static class DBHelper extends SQLiteOpenHelper{
        private static final int version = 1;

        public DBHelper(Context context) {
            super(context, DATABASENAME, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            final String WavesTable = "CREATE TABLE " + TABLE_PULSERAS + "(" +
                    TABLE_PULSERAS_ID_ + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TABLE_PULSERAS_Foto + " VARCHAR(255)," +
                    TABLE_PULSERAS_NOMBRE + " VARCHAR(255));";
            sqLiteDatabase.execSQL(WavesTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
