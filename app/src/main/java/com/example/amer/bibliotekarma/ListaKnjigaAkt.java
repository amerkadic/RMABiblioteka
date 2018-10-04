package com.example.amer.bibliotekarma;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Amer on 27.03.2018..
 */

public class ListaKnjigaAkt extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_knjiga_akt);

        ListView listaKnjiga = (ListView)findViewById(R.id.listaKnjiga);
        Button dPovratak = (Button)findViewById(R.id.dPovratak);

        //Knjiga m3 = new Knjiga("Elvis", "Presley", "rock", 11);

        //ArrayList<Knjiga> knjige =  new ArrayList<>();

       //  knjige = (ArrayList<Knjiga>)getIntent().getSerializableExtra("knjige1");

        Bundle bundle = getIntent().getExtras();
        ArrayList<Knjiga> knjige = null;
        if (bundle != null) {
            knjige = bundle.getParcelableArrayList("knjige");
        }

   /*
        Knjiga  k1= new Knjiga("Bram Stoker", "Dracula", "Horor", 11);
        Knjiga  k2= new Knjiga("Mary Shelley", "Frankenstein", "Horor", 11);
        Knjiga  k3= new Knjiga("Nora Roberts", "The Next Always", "Romance", 11);
        Knjiga  k4= new Knjiga("Stephen Hawking", "The Theory of Everything", "Science", 11);



        knjige.add(k1);
        knjige.add(k2);
        knjige.add(k3);
        knjige.add(k4);*/


        final AdapterKnjige aKnjiga;
       // aKnjiga = new AdapterKnjige(this, R.layout.element_liste, knjige);
        //listaKnjiga.setAdapter(aKnjiga);

        dPovratak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }


        });



    }
}
