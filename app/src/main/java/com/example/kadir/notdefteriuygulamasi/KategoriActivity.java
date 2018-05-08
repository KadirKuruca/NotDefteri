package com.example.kadir.notdefteriuygulamasi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class KategoriActivity extends AppCompatActivity {

    ListView lvKategori;
    EditText etKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        //Önceki Activity e geri dönmek için geri butonu ekleme
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lvKategori = findViewById(R.id.lvKategori);
        etKategori = findViewById(R.id.etKategori);

        String[] kategoriler = getResources().getStringArray(R.array.kategoriler);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.not_tek_satir,R.id.tvNotIcerik,kategoriler);
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
}
