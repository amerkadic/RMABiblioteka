package com.example.amer.bibliotekarma;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Amer on 22.05.2018..
 */

public class FragmentOnline extends Fragment implements DohvatiKnjige.IDohvatiKnjigeDone, DohvatiNajnovije.IDohvatiNajnovijeDone {
    private ArrayList<String> zanrovi=new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayList<Knjiga> knjige=new ArrayList<>();
    Spinner spinner, spin;
    DodajKnjigu dk;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.online_fragment,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        spin=(Spinner)getView().findViewById(R.id.sKategorije);
        spinner=(Spinner)getView().findViewById(R.id.sRezultat);
        Button dodaj=(Button)getView().findViewById(R.id.dAdd);
        Button nazad=(Button)getView().findViewById(R.id.dPovratak);
        Button tekst=(Button)getView().findViewById(R.id.dRun);
        final EditText tekstPretraga=(EditText)getView().findViewById(R.id.tekstUpit);

        if(getArguments().containsKey("kategorije")) {
            zanrovi=getArguments().getStringArrayList("kategorije");
            adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_expandable_list_item_1,zanrovi);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin.setAdapter(adapter);
        }

        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        tekst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tekstPretraga.getText().toString().contains("autor:")) {
                    String[] split=tekstPretraga.getText().toString().split(":");
                    new DohvatiNajnovije(FragmentOnline.this).execute(split[1]);
                }
                else {
                    new DohvatiKnjige(FragmentOnline.this).execute(tekstPretraga.getText().toString());
                }
            }
        });

        try {
            dk=(DodajKnjigu) getActivity();
        } catch(ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Log.e("a", String.valueOf(KategorijeAkt.knjige.size()));
                for(Knjiga knjiga : knjige) {
                    if(knjiga.getNaziv().equals(spinner.getSelectedItem().toString())) {
                     //   dk.onDodajKnjigu(spin.getSelectedItem().toString(),knjiga);
                        knjiga.setKategorija(spinner.getSelectedItem().toString());
                        KategorijeAkt.knjige.add(knjiga);

                    }
                }
            }
        });
    }

    @Override
    public void onDohvatiDone(ArrayList<Knjiga> k) {
        knjige=k;
        ArrayList<String> naziviKnjiga=new ArrayList<>();
        for(Knjiga knjiga : knjige) naziviKnjiga.add(knjiga.getNaziv());
        adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_expandable_list_item_1,naziviKnjiga);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onNajnovijeDone(ArrayList<Knjiga> k) {
        knjige=k;
        ArrayList<String> naziviKnjiga=new ArrayList<>();
        for(Knjiga knjiga : knjige) naziviKnjiga.add(knjiga.getNaziv());
        adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_expandable_list_item_1,naziviKnjiga);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public interface DodajKnjigu {
        public void onDodajKnjigu(String kategorija, Knjiga k);
    }
}
