package com.example.kadir.notdefteriuygulamasi;

import android.app.LoaderManager;
import android.content.ContentValues;
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
import android.widget.Toast;

import com.example.kadir.notdefteriuygulamasi.data.KategoriCursorAdapter;
import com.example.kadir.notdefteriuygulamasi.data.NotDefteriContract;
import com.example.kadir.notdefteriuygulamasi.data.NotDefteriContract.KategoriEntry;
import com.example.kadir.notdefteriuygulamasi.data.NotDefteriQueryHandler;

public class KategoriActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    ListView lvKategori;
    EditText etKategori;
    KategoriCursorAdapter adapter;
    Cursor cursor;
    long secilenKategoriID = -1;
    NotDefteriQueryHandler handler;

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

        handler = new NotDefteriQueryHandler(this.getContentResolver());

        lvKategori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) lvKategori.getItemAtPosition(position);
                secilenKategoriID = id;
                etKategori.setText(c.getString(1));
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

            String[] projection = {KategoriEntry._ID, KategoriEntry.COLUMN_KATEGORI};
            return new CursorLoader(this, KategoriEntry.CONTENT_URI,projection,null,null,"_id DESC");
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

    public void yeniKategori(View view) {

        etKategori.setText("");
        secilenKategoriID = -1;
    }

    public void saveOrUpdate(View view) {
        String yeniKategoriAdi = etKategori.getText().toString();

        ContentValues values = new ContentValues();
        values.put(KategoriEntry.COLUMN_KATEGORI,yeniKategoriAdi);

        if(secilenKategoriID == -1){

            //Insert

            handler.startInsert(1,null,KategoriEntry.CONTENT_URI,values);
            Toast.makeText(this, "Kategori Eklendi. ", Toast.LENGTH_SHORT).show();
            etKategori.setText("");
        }
        else
        {
            //Update
            String selection = KategoriEntry._ID+"=?";
            String[] args = {String.valueOf(secilenKategoriID)};
            handler.startUpdate(2,null, KategoriEntry.CONTENT_URI,values, selection,args);
            Toast.makeText(this,"Kategori Güncellendi.",Toast.LENGTH_SHORT).show();
        }
    }

    public void kategoriSil(View view) {
        //Delete
        String selection = KategoriEntry._ID+"=?";
        String[] args = {String.valueOf(secilenKategoriID)};
        handler.startDelete(3,null,KategoriEntry.CONTENT_URI,selection,args);
        Toast.makeText(this,"Kategori Silindi.",Toast.LENGTH_SHORT).show();
        etKategori.setText("");
    }
}
