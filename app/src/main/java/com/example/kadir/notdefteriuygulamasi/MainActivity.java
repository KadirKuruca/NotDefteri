package com.example.kadir.notdefteriuygulamasi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import com.example.kadir.notdefteriuygulamasi.data.NotDefteriContract.KategoriEntry;
import com.example.kadir.notdefteriuygulamasi.data.NotlarCursorAdapter;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ListView lvNot;
    private static final int TUM_NOTLAR = -1;
    private static final int TUM_KATEGORILER = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = findViewById(R.id.spinner);
        lvNot = findViewById(R.id.lvNot);
        Cursor cursor = notlariGoster();


        NotlarCursorAdapter adapter = new NotlarCursorAdapter(this,cursor,false);
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
        if (id == R.id.action_kategori){
            Intent intent = new Intent(this,KategoriActivity.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_kategorileri_sil){
            kategorileriSil(TUM_KATEGORILER);
            kategoriGoster();
            return true;
        }

        if(id == R.id.action_notlari_sil){
            notlariSil(TUM_NOTLAR);
            notlariGoster();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_test_notlar){
            testNotOlustur();
            return true;
        }

        if(id == R.id.action_test_kategoriler){
            testKategoriOlustur();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void testKategoriOlustur() {

        Uri _uri = null;
        for(int i=1;i<=5; i++){

            ContentValues values = new ContentValues();
            values.put(KategoriEntry.COLUMN_KATEGORI,"Kategori #"+i);
            _uri = getContentResolver().insert(KategoriEntry.CONTENT_URI,values);
        }
        if(_uri != null){
            Toast.makeText(this, "Test Kategoriler Eklendi. "+_uri, Toast.LENGTH_SHORT).show();
            Log.e("KATEGORİLER"," "+_uri);
            kategoriGoster();
        }
    }

    private void testNotOlustur() {
        Uri _uri = null;

        for(int i=1;i<=5;i++){

            ContentValues values = new ContentValues();
            values.put(NotlarEntry.COLUMN_NOTE_ICERIK,"Yeni Eklenen Not #"+i);
            values.put(NotlarEntry.COLUMN_KATEGORI_ID,i);
            values.put(NotlarEntry.COLUMN_OLUSTURMA_TARIHI,"16-05-2018");
            values.put(NotlarEntry.COLUMN_BITIS_TARIHI,"18-05-2018");
            values.put(NotlarEntry.COLUMN_YAPILDI,0);

            _uri = getContentResolver().insert(NotlarEntry.CONTENT_URI,values);
        }
        if(_uri != null){
            Toast.makeText(this, "Test Notları Eklendi. "+_uri, Toast.LENGTH_SHORT).show();
            Log.e("NOTLAR"," "+_uri);
            notlariGoster();
        }
    }


    private void kategoriEkle(){

        ContentValues values = new ContentValues();
        values.put(KategoriEntry.COLUMN_KATEGORI,"Deneme");
        Uri _uri = getContentResolver().insert(KategoriEntry.CONTENT_URI,values);
        Toast.makeText(this, "Kategori Eklendi : "+_uri, Toast.LENGTH_LONG).show();

    }

    private void kategoriGoster(){

        String[] projection = {"_id","kategori"};
        String tumKategoriler="";

        Cursor cursor = getContentResolver().query(KategoriEntry.CONTENT_URI,projection,null,null,null);

        while(cursor.moveToNext()){

            String id = cursor.getString(0);
            String kategori = cursor.getString(1);

            tumKategoriler = tumKategoriler+"id : "+id+" kategori : "+kategori+"\n";
        }

        Toast.makeText(this, ""+tumKategoriler, Toast.LENGTH_SHORT).show();
        Log.e("KATEGORİLER",""+tumKategoriler);
    }

    private void notEkle() {
        ContentValues values = new ContentValues();
        values.put(NotlarEntry.COLUMN_NOTE_ICERIK,"Yeni Eklenen Not1");
        values.put(NotlarEntry.COLUMN_KATEGORI_ID,1);
        values.put(NotlarEntry.COLUMN_OLUSTURMA_TARIHI,"16-05-2018");
        values.put(NotlarEntry.COLUMN_BITIS_TARIHI,"18-05-2018");
        values.put(NotlarEntry.COLUMN_YAPILDI,0);

        Uri _uri = null;
        _uri = getContentResolver().insert(NotlarEntry.CONTENT_URI,values);
        if(_uri != null){
            Toast.makeText(this, "Not Kaydedildi."+_uri, Toast.LENGTH_SHORT).show();
        }
    }

    private Cursor notlariGoster(){
        String[] projection={"notlar._id","notlar.notIcerik","notlar.olusturulmaTarihi","kategoriler._id","kategoriler.kategori"};
        Cursor cursor = getContentResolver().query(NotlarEntry.CONTENT_URI,projection,null,null,null);

        /*String tumNotlar = "";
        while(cursor.moveToNext()){
            for(int i=0;i<=4;i++){
                tumNotlar = tumNotlar+cursor.getString(i)+" - ";
            }
            tumNotlar = tumNotlar+"\n";
        }
        Toast.makeText(this, ""+tumNotlar, Toast.LENGTH_SHORT).show();
        Log.e("NOTLAR : ",""+tumNotlar);*/

        return cursor;
    }

    private void notlariGuncelle(){

        ContentValues values = new ContentValues();
        values.put(NotlarEntry.COLUMN_NOTE_ICERIK,"Update Edilen Not");
        values.put(NotlarEntry.COLUMN_KATEGORI_ID,1);
        values.put(NotlarEntry.COLUMN_OLUSTURMA_TARIHI,17-05-2018);
        values.put(NotlarEntry.COLUMN_BITIS_TARIHI,19-05-2018);
        values.put(NotlarEntry.COLUMN_YAPILDI,1);

        int etkilenenSatir = getContentResolver().update(NotlarEntry.CONTENT_URI,values, "_id = ?",new String[]{"5"});
        if(etkilenenSatir != 0){
            Toast.makeText(this, "Not Silindi : "+etkilenenSatir, Toast.LENGTH_SHORT).show();
        }
    }

    private void notlariSil(int silinecekID){

        String[] args = {String.valueOf(silinecekID)};
        String selection="_id=?";
        if(silinecekID == TUM_NOTLAR){
            args = null;
            selection = null;
        }

        int silinenSatir = getContentResolver().delete(NotlarEntry.CONTENT_URI,selection, args);
        if(silinenSatir != 0){
            Toast.makeText(this, "Not Silindi : "+silinenSatir, Toast.LENGTH_SHORT).show();
        }
    }

    private void kategorileriSil(int silinecekID){

        String[] args = {String.valueOf(silinecekID)};
        String selection = "_id=?";
        if(silinecekID == TUM_KATEGORILER){
            args = null;
            selection = null;
        }

        int silinenSatir = getContentResolver().delete(KategoriEntry.CONTENT_URI,selection, args);
        if(silinenSatir != 0){
            Toast.makeText(this, "Kategori Silindi : "+silinenSatir, Toast.LENGTH_SHORT).show();
        }
    }
}
