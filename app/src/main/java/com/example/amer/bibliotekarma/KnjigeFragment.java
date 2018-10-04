

package com.example.amer.bibliotekarma;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class KnjigeFragment extends Fragment {

    private Knjiga knjiga;
    private AdapterKnjige adapter;
    private ArrayList<String> kategorije;
    private ArrayList<Knjiga> lista;
    private ArrayList<Knjiga> knjige = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_knjige,container,false);

        if (getArguments() != null && getArguments().containsKey("kategorija"))
        kategorije=getArguments().getStringArrayList("kategorije");

        ListView lv=(ListView)v.findViewById(R.id.listaKnjiga);
        lista=new ArrayList<>();
        knjige=KategorijeAkt.knjige;

        if(getArguments().containsKey("zanr")) {
            String zanr=getArguments().getString("zanr");

            for(Knjiga k : knjige) {
              // if(zanr.equals(k.getKategorija())) {
                    lista.add(k);
              //  }
            }
        }
        else if(getArguments().containsKey("autor")) {
            String a=getArguments().getString("autor");
            for(Knjiga k : knjige) {
              //  if(a.equals(k.getImeAutora())) {
                    lista.add(k);
               // }
            }
        }
       /*Log.e("a", String.valueOf(KategorijeAkt.knjige.size()));

        ArrayList<Autor> autori = new ArrayList<Autor>();
        Autor amer = new Autor("Amer","18");
        autori.add(amer);
        int broj= 18;
        URL url = null;
        try {
            url = new URL("http://www.slatina.net/wp-content/uploads/2013/07/knjige.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Knjiga  tmp = new Knjiga("10","Artofwar", autori, "agasg", "15.15",url, broj);
        lista.add(tmp);*/
        adapter = new AdapterKnjige(getActivity(), lista);
        lv.setAdapter(adapter);

        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button povratak=(Button)getView().findViewById(R.id.dPovratak);
        ListView lv=(ListView)getView().findViewById(R.id.listaKnjiga);
        knjige=KategorijeAkt.knjige;

        povratak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListeFragment lf=new ListeFragment();
                Bundle arg=new Bundle();

                arg.putStringArrayList("kategorije", kategorije);
                arg.putParcelableArrayList("knjige", knjige);

                lf.setArguments(arg);

                getFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getFragmentManager().beginTransaction().replace(R.id.mjestoF1,lf).commit();
            }
        });
    }
}
