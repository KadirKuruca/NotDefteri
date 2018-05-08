package com.example.kadir.notdefteriuygulamasi;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.kadir.notdefteriuygulamasi.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ListView lvNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = findViewById(R.id.spinner);
        lvNot = findViewById(R.id.lvNot);

        //DatabaseHelper sınıfını kullanmak için nesne oluşturduk.
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

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
}
