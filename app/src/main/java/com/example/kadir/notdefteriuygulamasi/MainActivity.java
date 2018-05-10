package com.example.kadir.notdefteriuygulamasi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kadir.notdefteriuygulamasi.data.DatabaseHelper;
import com.example.kadir.notdefteriuygulamasi.data.NotDefteriContract;
import com.example.kadir.notdefteriuygulamasi.data.NotDefteriContract.NotlarEntry;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ListView lvNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notOlustur();
        notGuncelle();
        notSil();
        notlariOku();

        spinner = findViewById(R.id.spinner);
        lvNot = findViewById(R.id.lvNot);


        String[] notIcerik = getResources().getStringArray(R.array.notlar);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.not_tek_satir, R.id.tvNotIcerik, notIcerik);
        lvNot.setAdapter(adapter);

        lvNot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this,NotActivity.class);
                String putNot = lvNot.getItemAtPosition(position).toString();
                intent.putExtra("put_not",""+putNot);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NotActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_kategori){
            Intent intent = new Intent(this,KategoriActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void notOlustur(){

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String insertSorgusu = "INSERT INTO notlar ("
                + NotDefteriContract.NotlarEntry.COLUMN_NOTE_ICERIK + ","
                + NotDefteriContract.NotlarEntry.COLUMN_KATEGORI_ID + ","
                + NotDefteriContract.NotlarEntry.COLUMN_OLUSTURMA_TARİHİ + ","
                + NotDefteriContract.NotlarEntry.COLUMN_BITIS_TARIHI + ","
                + NotDefteriContract.NotlarEntry.COLUMN_YAPILDI + ")"
                + " VALUES (\"SPORA GIT\", 1, \"07-05-2018\",\"\",0)";

        db.execSQL(insertSorgusu); // Bu geriye bir şey döndürmediği için tavsiye edilmez.

        ContentValues yenikayit = new ContentValues(); // Bu şekilde veri ekleme tavsiye edilir. Geriye long tipinde değer döndürür.
        yenikayit.put(NotDefteriContract.NotlarEntry.COLUMN_NOTE_ICERIK,"OKULA GİT");
        yenikayit.put(NotDefteriContract.NotlarEntry.COLUMN_KATEGORI_ID, 1);
        yenikayit.put(NotDefteriContract.NotlarEntry.COLUMN_OLUSTURMA_TARİHİ, "06-05-2018");
        yenikayit.put(NotDefteriContract.NotlarEntry.COLUMN_BITIS_TARIHI, "09-05-2018");
        yenikayit.put(NotDefteriContract.NotlarEntry.COLUMN_YAPILDI, 0);

        long id = db.insert(NotDefteriContract.NotlarEntry.TABLE_NAME,null,yenikayit);


    }

    private void notlariOku() {
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {NotlarEntry._ID,
                            NotlarEntry.COLUMN_NOTE_ICERIK,
                            NotlarEntry.COLUMN_OLUSTURMA_TARİHİ,
                            NotlarEntry.COLUMN_BITIS_TARIHI,
                            NotlarEntry.COLUMN_YAPILDI,
                            NotlarEntry.COLUMN_KATEGORI_ID};

        String selection = NotlarEntry.COLUMN_KATEGORI_ID + " = ?";
        String[] selectionArgs = {"1"};

        Cursor c = db.query(NotlarEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null,null);

        int i = c.getCount();
        Toast.makeText(this, "Satır Sayısı : "+i, Toast.LENGTH_SHORT).show();

        String tumNotlar = "";
        while(c.moveToNext()){

            for(int j=0; j<=4 ; j++){
                tumNotlar += c.getString(j) + " - ";
            }
            tumNotlar += "\n";
        }

        Log.e("VERİLER : ",tumNotlar);
        c.close();
        db.close();
    }

    private void notGuncelle() {

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues guncellenen = new ContentValues();
        guncellenen.put(NotlarEntry.COLUMN_NOTE_ICERIK,"Not Güncellendi.");
        String[] args = {"5"};

        int etkilenenSatirSayisi = db.update(NotlarEntry.TABLE_NAME,guncellenen,NotlarEntry._ID + "= ?",args);
        Toast.makeText(this, "Guncellenen Satır Sayısı : "+etkilenenSatirSayisi, Toast.LENGTH_SHORT).show();
    }

    private void notSil(){

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        int silinenSatirSayisi = 0;
        for(int i=20; i<=28;i++){
            String[] args = {String.valueOf(i)};
            silinenSatirSayisi += db.delete(NotlarEntry.TABLE_NAME, NotlarEntry._ID + "= ?",args);
        }
        Toast.makeText(this, "Silinen Satır Sayısı = "+silinenSatirSayisi, Toast.LENGTH_SHORT).show();
    }
}
