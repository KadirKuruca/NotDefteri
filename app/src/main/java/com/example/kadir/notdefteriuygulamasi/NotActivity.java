package com.example.kadir.notdefteriuygulamasi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

public class NotActivity extends AppCompatActivity {

    Spinner spinnerKategori;
    EditText etChangeNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinnerKategori = findViewById(R.id.spinnerKategori);
        etChangeNot = findViewById(R.id.etChangeNot);

        Intent intent = getIntent();
        if(intent != null){
            String gelenNot = intent.getStringExtra("put_not");
            etChangeNot.setText(gelenNot);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
