

package com.example.amer.bibliotekarma;

        import android.app.Fragment;
        import android.app.FragmentManager;
        import android.content.ContentValues;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Filter;
         import android.widget.FrameLayout;
        import android.widget.ListView;

        import java.util.ArrayList;

        import static com.example.amer.bibliotekarma.BazaOpenHelper.DATABASE_TABLE_KATEGORIJA;
        import static com.example.amer.bibliotekarma.BazaOpenHelper.KATEGORIJA_NAZIV;

/**
 * Created by Amer on 14.04.2018..
 */

public class ListeFragment extends Fragment {

    private ArrayList<String> kategorije;
    private ArrayAdapter adapter;
    private DodajKnjiguClick dodajClick;
    private ArrayList<Knjiga> knjige;
    private ArrayList<Autor> autoriLista;
    private OnItemClick oic;
    private DodajOnlineClick doc;
    private boolean jesuLiKategorije=true;
    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_liste,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        knjige=new ArrayList<>();
        kategorije=new ArrayList<>();
        autoriLista=new ArrayList<>();


        if(getArguments().containsKey("kategorije")) kategorije=getArguments().getStringArrayList("kategorije");

        if(getArguments().containsKey("knjige")) {
            knjige=getArguments().getParcelableArrayList("knjige");
        }

        for (Knjiga knjiga : knjige) {
            boolean imaGa = false;
            String naziv = knjiga.getImeAutora();
            for (Autor a : autoriLista) {
                if (a.getImeiPrezime().equals(naziv)) {
                    imaGa = true;
                    a.dodajKnjigu();
                }
            }
            if (!imaGa) {
                autoriLista.add(new Autor(knjiga.getImeAutora()));
            }
        }


        final Button pretraga=(Button)getView().findViewById(R.id.dPretraga);
        final Button dodajKategoriju=(Button)getView().findViewById(R.id.dDodajKategoriju);
        final EditText tekst=(EditText)getView().findViewById(R.id.tekstPretraga);
        Button dodajKnjigu=(Button)getView().findViewById(R.id.dDodajKnjigu);
        Button autori=(Button)getView().findViewById(R.id.dAutori);

        dodajKategoriju.setEnabled(false);

        pretraga.setVisibility(View.GONE);
        dodajKategoriju.setVisibility(View.GONE);
        tekst.setVisibility(View.GONE);

        Button kategorija=(Button)getView().findViewById(R.id.dKategorije);
        final ListView lv=(ListView)getView().findViewById(R.id.listaKategorija);


         Button dodajKnjiguOnline=(Button)getView().findViewById(R.id.dDodajOnline);

        try {
            dodajClick=(DodajKnjiguClick)getActivity();
        } catch(ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }

        dodajKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dodajClick.onClick();
            }
        });

        try {
            oic=(OnItemClick)getActivity();
        } catch(ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                oic.onItemClicked(i,jesuLiKategorije,autoriLista,knjige);
            }
        });

        try {
            doc=(DodajOnlineClick)getActivity();
        } catch(ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }

        dodajKnjiguOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doc.onOnlineClick();
            }
        });

        kategorija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pretraga.setVisibility(View.VISIBLE);
                dodajKategoriju.setVisibility(View.VISIBLE);
                tekst.setVisibility(View.VISIBLE);
                adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_expandable_list_item_1,KategorijeAkt.kategorija);
                lv.setAdapter(adapter);
                jesuLiKategorije=true;

            }
        });

        autori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pretraga.setVisibility(View.GONE);
                dodajKategoriju.setVisibility(View.GONE);
                tekst.setVisibility(View.GONE);

                lv.setAdapter(null);

                ArrayList<String> ispisAutori = new ArrayList<>();
                for (Autor a : autoriLista) {
                    ispisAutori.add(a.toString());
                }
                adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, KategorijeAkt.autori);
                lv.setAdapter(adapter);

                jesuLiKategorije=false;
            }
        });

        pretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.getFilter().filter(tekst.getText().toString(), new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int i) {
                        if (i == 0) dodajKategoriju.setEnabled(true);
                        else dodajKategoriju.setEnabled(false);
                    }
                });
            }
        });

        dodajKategoriju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*ContentValues kategorijaBaza = new ContentValues();
                kategorijaBaza.put(KATEGORIJA_NAZIV,tekst.getText().toString());


                SQLiteDatabase db = BazaOpenHelper.getWritableDatabase();
                db.insert(BazaOpenHelper.DATABASE_TABLE_KATEGORIJA,null,kategorijaBaza);*/
                long index = KategorijeAkt.db.dodajKategoriju(tekst.getText().toString());
                ArrayList<String> listaKategorija = KategorijeAkt.db.vratiKategorije();
                for(String b : listaKategorija) {
                    adapter.add(b);
                }
                kategorije.add(tekst.getText().toString());
                KategorijeAkt.kategorija.add(tekst.getText().toString());
                adapter.notifyDataSetChanged();
                tekst.setText("");
                dodajKategoriju.setEnabled(false);
                adapter.getFilter().filter("");
            }
        });
    }

    public interface OnItemClick {
        public void onItemClicked(int i, boolean jesuLiKategorije, ArrayList<Autor> autoriLista, ArrayList<Knjiga> knjige);
    }

    public interface DodajKnjiguClick {
        public void onClick();
    }

    public interface DodajOnlineClick {
        public void onOnlineClick();
    }
}
