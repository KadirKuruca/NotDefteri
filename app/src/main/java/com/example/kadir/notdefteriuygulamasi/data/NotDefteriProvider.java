package com.example.kadir.notdefteriuygulamasi.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class NotDefteriProvider extends ContentProvider {

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int URICODE_NOTLAR = 1;
    private static final int URICODE_KATEGORILER = 10;

    static {
        matcher.addURI(NotDefteriContract.CONTENT_AUTHORITY, NotDefteriContract.PATH_NOTLAR,URICODE_NOTLAR);
        matcher.addURI(NotDefteriContract.CONTENT_AUTHORITY,NotDefteriContract.PATH_KATEGORILER,URICODE_KATEGORILER);
    }

    private SQLiteDatabase db;
    private DatabaseHelper helper;

    @Override
    public boolean onCreate() {

        helper = new DatabaseHelper(getContext());
        db = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        //İki Tabloyu birleştirme işlemi yapılıyor.

        SQLiteQueryBuilder builder;
        String tabloBirlestir = "notlar inner join kategoriler on notlar.kategoriID = kategoriler._id";

        Cursor cursor;
        switch (matcher.match(uri)){
            case URICODE_NOTLAR:
                builder = new SQLiteQueryBuilder();
                builder.setTables(tabloBirlestir);
                cursor = builder.query(db,projection,selection,selectionArgs,null,null,null);
                break;

            case URICODE_KATEGORILER:
                cursor = db.query(NotDefteriContract.KategoriEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
                break;
            default:
                throw new IllegalArgumentException("Bilinmeyen Uri : "+uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        switch (matcher.match(uri)){
            case URICODE_NOTLAR:
                return kayitEkle(uri,values,NotDefteriContract.NotlarEntry.TABLE_NAME);
            case URICODE_KATEGORILER:
                return kayitEkle(uri,values,NotDefteriContract.KategoriEntry.TABLE_NAME);
            default:
                throw new IllegalArgumentException("Bilinmeyen Uri : "+uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        switch (matcher.match(uri)){

            case URICODE_NOTLAR :
                return kayitSil(uri,selection,selectionArgs, NotDefteriContract.NotlarEntry.TABLE_NAME);
            case URICODE_KATEGORILER:
                return kayitSil(uri,selection,selectionArgs, NotDefteriContract.KategoriEntry.TABLE_NAME);

            default:
                throw new IllegalArgumentException("Bilinmeyen Uri : "+uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        switch (matcher.match(uri)){
            case URICODE_NOTLAR:
                return kayitGuncelle(uri,values,selection,selectionArgs, NotDefteriContract.NotlarEntry.TABLE_NAME);
            case URICODE_KATEGORILER:
                return kayitGuncelle(uri,values,selection,selectionArgs, NotDefteriContract.KategoriEntry.TABLE_NAME);

            default:
                throw new IllegalArgumentException("Bilinmeyen Uri : "+uri);
        }
    }

    private Uri kayitEkle(Uri uri, ContentValues values, String tableName){

        long id = db.insert(tableName,null,values);
        if(id == -1){
            Log.e("NotDefteriProvider","INSERT HATA"+uri);
            return null;
        }

        return ContentUris.withAppendedId(uri,id);
    }

    private int kayitGuncelle(Uri uri, ContentValues values,String selection,String[] selectionArgs,String tabloAdi){

        int id = db.update(tabloAdi,values,selection,selectionArgs);
        if(id == 0)
        {
            Log.e("HATA","UPDATE HATASI");
            return -1;
        }
        return id;
    }

    private int kayitSil(Uri uri,String selection,String[] selectionArgs, String tabloAdi){

        int id = db.delete(tabloAdi,selection,selectionArgs);
        if(id == 0){
            Log.e("HATA","DELETE HATASI");
            return -1;
        }
        return id;
    }
}
