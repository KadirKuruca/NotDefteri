package com.example.kadir.notdefteriuygulamasi.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kadir.notdefteriuygulamasi.data.NotDefteriContract.KategoriEntry;
import com.example.kadir.notdefteriuygulamasi.data.NotDefteriContract.NotlarEntry;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "notdefterim.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_KATEGORILER_CREATE =
            "CREATE TABLE " + KategoriEntry.TABLE_NAME + " ("+
                    KategoriEntry._ID + " INTEGER PRIMARY KEY, "+
                    KategoriEntry.COLUMN_KATEGORI + "TEXT)";

    private static final String TABLE_NOTLAR_CREATE =
            "CREATE TABLE " + NotlarEntry.TABLE_NAME + " ("+
                    NotlarEntry._ID + " INTEGER PRIMARY KEY,"+
                    NotlarEntry.COLUMN_NOTE_ICERIK + " TEXT, "+
                    NotlarEntry.COLUMN_OLUSTURMA_TARİHİ + " TEXT DEFAULT CURRENT_TIMESTAMP, "+
                    NotlarEntry.COLUMN_BITIS_TARIHI + " TEXT, "+
                    NotlarEntry.COLUMN_YAPILDI + " INTEGER, "+
                    NotlarEntry.COLUMN_KATEGORI_ID + " INTEGER, "+
                    "FOREIGN KEY ( " +NotlarEntry.COLUMN_KATEGORI_ID +")" + " REFERENCES "+ KategoriEntry.TABLE_NAME +
                    "("+KategoriEntry._ID + ") " + ")";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_KATEGORILER_CREATE);
        db.execSQL(TABLE_NOTLAR_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +KategoriEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +NotlarEntry.TABLE_NAME);

        onCreate(db);
    }
}
