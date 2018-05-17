package com.example.kadir.notdefteriuygulamasi.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.kadir.notdefteriuygulamasi.R;

public class NotlarCursorAdapter extends CursorAdapter {

    public NotlarCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.not_tek_satir,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView not = view.findViewById(R.id.tvNotIcerik);

        int notColumnIndex = cursor.getColumnIndex(NotDefteriContract.NotlarEntry.COLUMN_NOTE_ICERIK);
        String notIcerigi = cursor.getString(notColumnIndex);
        not.setText(notIcerigi);
    }
}
