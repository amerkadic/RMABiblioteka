package com.example.amer.bibliotekarma;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Amer on 26.03.2018..
 */

public class DodavanjeKnjigeAkt extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dodavanje_knjige_akt);

        final ImageView naslovnaStr = (ImageView)findViewById(R.id.naslovnaStr);
        final EditText imeAutora = (EditText)findViewById(R.id.imeAutora);
        final EditText nazivKnjige = (EditText)findViewById(R.id.nazivKnjige);
        final Button nadjiSliku = (Button)findViewById(R.id.dNadjiSliku);
        final Button upisiKnjigu = (Button)findViewById(R.id.dUpisiKnjigu);
        final Button ponisti =  (Button)findViewById(R.id.dPonisti);
        final Spinner kategorijaKnjige = (Spinner)findViewById(R.id.sKategorijaKnjige);



        ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra("knjiga");


        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapt.setDropDownViewResource(android.R.layout.simple_list_item_1);
        kategorijaKnjige.setAdapter(adapt);
        adapt.notifyDataSetChanged();

        upisiKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<Knjiga> knjige = new ArrayList<>();
             //   Knjiga k = new Knjiga(imeAutora.getText().toString(), nazivKnjige.getText().toString(),
                //     kategorijaKnjige.getSelectedItem().toString(), R.drawable.slika1);
           //  knjige.add(k);

            }
        });

        nadjiSliku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ponisti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });




    }
}
