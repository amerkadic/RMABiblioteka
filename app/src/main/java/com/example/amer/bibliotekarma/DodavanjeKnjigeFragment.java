package com.example.amer.bibliotekarma;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DodavanjeKnjigeFragment extends Fragment {

    private Boolean b=true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dodavanje_knjige, container, false);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Button ponisti =  (Button) getView().findViewById(R.id.dPonisti);
        final ImageView naslovnaStr = (ImageView)getView().findViewById(R.id.naslovnaStr);
        final EditText imeAutora = (EditText)getView().findViewById(R.id.imeAutora);
        final EditText nazivKnjige = (EditText)getView().findViewById(R.id.nazivKnjige);
        final Button nadjiSliku = (Button)getView().findViewById(R.id.dNadjiSliku);
        final Button upisiKnjigu = (Button)getView().findViewById(R.id.dUpisiKnjigu);
        final Spinner kategorijaKnjige = (Spinner)getView().findViewById(R.id.sKategorijaKnjige);




        ArrayAdapter<String> adapt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, KategorijeAkt.kategorija);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategorijaKnjige.setAdapter(adapt);
        adapt.notifyDataSetChanged();

        upisiKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // final ArrayList<Knjiga> knjige = new ArrayList<>();
                   Knjiga k = new Knjiga(imeAutora.getText().toString(), nazivKnjige.getText().toString(),
                    kategorijaKnjige.getSelectedItem().toString(), "default");
                  KategorijeAkt.knjige.add(k);

                for (String autor : KategorijeAkt.autori) {

                    if (autor.equals(imeAutora.getText().toString()))
                        b=false;
                }
                if(b==true)  KategorijeAkt.autori.add(imeAutora.getText().toString());

                imeAutora.setText("");
                nazivKnjige.setText("");
            }



        });

        ponisti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().popBackStack();

            }});
    }
}
