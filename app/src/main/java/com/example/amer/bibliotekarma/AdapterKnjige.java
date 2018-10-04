package com.example.amer.bibliotekarma;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amer on 31.03.2018..
 */

public class AdapterKnjige extends ArrayAdapter<Knjiga> {

    private Context context;
    private ArrayList<Knjiga> knjige;
    private PreporucionClick poc;

    public AdapterKnjige(@NonNull Context context, @NonNull ArrayList<Knjiga> knjige) {
        super(context,0, knjige);
        this.context = context;
        this.knjige=knjige;
    }






    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        View v = LayoutInflater.from(context).inflate(R.layout.element_liste,parent,false);
       // if(knjige.get(position).getSelektovanaBoja().equals("#ffaabbed"))
          //  v.setBackgroundColor(Color.parseColor("#ffaabbed"));
        TextView Naziv,Autor,DatumObjavljivanja,Opis,BrojStranica;
        ImageView Slika;
        Button dPreporuci;
        Naziv= v.findViewById(R.id.eNaziv);
        Autor = v.findViewById(R.id.eAutor);
        DatumObjavljivanja = v.findViewById(R.id.eDatumObjavljivanja);
        Opis = v.findViewById(R.id.eOpis);
        BrojStranica = v.findViewById(R.id.eBrojStranica);
        Slika = v.findViewById(R.id.eNaslovna);
        dPreporuci = v.findViewById(R.id.dPreporuci);
        Naziv.setText(knjige.get(position).getNazivKnjige());
        Autor.setText(knjige.get(position).getAutori().get(0).getImeiPrezime());
        DatumObjavljivanja.setText(knjige.get(position).getDatumObjavljivanja());
        Opis.setText(knjige.get(position).getOpis());
       BrojStranica.setText(String.valueOf(knjige.get(position).getBrojStranica()));






        //if(knjige.get(position).getSlika() == null) knjige.get(position).setSlika(R.drawable.slika1);
        Picasso.with(getContext()).load(String.valueOf(knjige.get(position).getSlika())).into(Slika);

        try {
            poc = (PreporucionClick)context;

        }
        catch (ClassCastException E)
        {
            throw new ClassCastException(context.toString());
        }

        dPreporuci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poc.onPreporuciClick(knjige.get(position));
            }
        });

        return v;
    }


    @Override
    public int getCount() {
        return knjige.size();
    }
/*
    @Override
   public Object getItem(int i) {
        return knjige.get(i);
    }*/

    @Override
    public long getItemId(int i) {
        return i;
    }

    public interface PreporucionClick{
        public void onPreporuciClick(Knjiga k);
    }
}
