package com.example.kadir.notdefteriuygulamasi.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kadir.notdefteriuygulamasi.R;

public class KategoriCursorAdapter extends CursorAdapter {

    public KategoriCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View v = LayoutInflater.from(context).inflate(R.layout.kategori_tek_satir,parent,false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView kategori = view.findViewById(R.id.tvKategori);
        String kategoriAdi = cursor.getString(cursor.getColumnIndex(NotDefteriContract.KategoriEntry.COLUMN_KATEGORI));
        kategori.setText(kategoriAdi);
    }
}
