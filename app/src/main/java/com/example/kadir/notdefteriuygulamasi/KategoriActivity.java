package com.example.kadir.notdefteriuygulamasi;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.kadir.notdefteriuygulamasi.data.KategoriCursorAdapter;
import com.example.kadir.notdefteriuygulamasi.data.NotDefteriContract;

public class KategoriActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    ListView lvKategori;
    EditText etKategori;
    KategoriCursorAdapter adapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        //Önceki Activity e geri dönmek için geri butonu ekleme
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lvKategori = findViewById(R.id.lvKategori);
        etKategori = findViewById(R.id.etKategori);

        getLoaderManager().initLoader(112,null,this);

        adapter = new KategoriCursorAdapter(this,cursor,false);
        lvKategori.setAdapter(adapter);

        lvKategori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tiklananKategori = lvKategori.getItemAtPosition(position).toString();
                etKategori.setText(tiklananKategori);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if(id == 112){

            String[] projection = {NotDefteriContract.KategoriEntry._ID, NotDefteriContract.KategoriEntry.COLUMN_KATEGORI};
            return new CursorLoader(this, NotDefteriContract.KategoriEntry.CONTENT_URI,projection,null,null,null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
