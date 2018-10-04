package com.example.amer.bibliotekarma;


import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import static com.example.amer.bibliotekarma.BazaOpenHelper.DATABASE_TABLE_KATEGORIJA;

public class KategorijeAkt extends AppCompatActivity implements ListeFragment.DodajKnjiguClick,ListeFragment.OnItemClick, ListeFragment.DodajOnlineClick, FragmentOnline.DodajKnjigu, AdapterKnjige.PreporucionClick{

    private ArrayList<String> kategorije;
    public static ArrayList<Knjiga> knjige = new ArrayList<Knjiga>();
    public  ArrayList<Knjiga> knjiga =new ArrayList<>();
    public static ArrayList<String> kategorija = new ArrayList<String>();
    public static ArrayList<String> autori = new ArrayList<String>();
    private boolean sirokiLayout=false;
    private boolean dozvolaZaKontakte=false;
    public static    BazaOpenHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorije_akt);
        db = new BazaOpenHelper(this, DATABASE_TABLE_KATEGORIJA,null,BazaOpenHelper.DATABASE_VERSION);






        if(dozvolaZaKontakte==false) ActivityCompat.requestPermissions(KategorijeAkt.this, new String[]{Manifest.permission.READ_CONTACTS},1);

        kategorije=new ArrayList<>();

        if(savedInstanceState!=null) {
            //knjige=savedInstanceState.getParcelableArrayList("knjige");
            knjiga= KategorijeAkt.knjige;
            kategorije=KategorijeAkt.kategorija;
           // kategorije=savedInstanceState.getStringArrayList("kategorije");
        }

        FragmentManager fm=getFragmentManager();
        FrameLayout layoutKnjiga=(FrameLayout)findViewById(R.id.mjestoF2);

        if(layoutKnjiga!=null) {
            sirokiLayout=true;
            KnjigeFragment kf;
            kf=(KnjigeFragment)fm.findFragmentById(R.id.mjestoF2);
            if(kf==null) {
                kf=new KnjigeFragment();
                Bundle arg=new Bundle();
                arg.putStringArrayList("kategorije", kategorije);
                arg.putParcelableArrayList("knjige", knjiga);
                kf.setArguments(arg);

                fm.beginTransaction().replace(R.id.mjestoF2,kf).commit();
            }
        }

        ListeFragment lf=new ListeFragment();
        if(savedInstanceState==null) {
            lf = (ListeFragment) fm.findFragmentById(R.id.mjestoF1);
        }
        if (lf == null) {
            lf = new ListeFragment();
            Bundle arg = new Bundle();
            arg.putStringArrayList("kategorije", kategorije);
            arg.putParcelableArrayList("knjige", knjiga);
            lf.setArguments(arg);

            fm.beginTransaction().replace(R.id.mjestoF1, lf).commit();
        } else {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onClick() {
        Bundle arg=new Bundle();
        arg.putStringArrayList("kategorije", kategorije);
        arg.putParcelableArrayList("knjige", knjiga);

        DodavanjeKnjigeFragment dodavanjeKnjige=new DodavanjeKnjigeFragment();
        dodavanjeKnjige.setArguments(arg);

        getFragmentManager().beginTransaction().replace(R.id.mjestoF1,dodavanjeKnjige).addToBackStack(null).commit();
    }

    @Override
    public void onItemClicked(int i, boolean jesuLiKategorije, ArrayList<Autor> autoriLista, ArrayList<Knjiga> knjige) {
        Bundle arg=new Bundle();
        arg.putStringArrayList("kategorije", kategorije);
        arg.putParcelableArrayList("knjige", knjiga);
        if(jesuLiKategorije) {
            arg.putString("zanr", KategorijeAkt.kategorija.get(i));
        }
        else {
            arg.putString("autor",KategorijeAkt.autori.get(i));
        }

        KnjigeFragment kf=new KnjigeFragment();
        kf.setArguments(arg);

        if(sirokiLayout) {
            getFragmentManager().beginTransaction().replace(R.id.mjestoF2,kf).commit();
        }
        else {
            getFragmentManager().beginTransaction().replace(R.id.mjestoF1,kf).addToBackStack(null).commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("knjige",knjiga);
        savedInstanceState.putStringArrayList("kategorije", kategorije);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1 && grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            dozvolaZaKontakte=true;
        }
    }

    @Override
    public void onOnlineClick() {
        Bundle arg=new Bundle();
        arg.putStringArrayList("kategorije", kategorije);
        arg.putParcelableArrayList("knjige", knjiga);

        FragmentOnline dodavanjeKnjige=new FragmentOnline();
        dodavanjeKnjige.setArguments(arg);

        getFragmentManager().beginTransaction().replace(R.id.mjestoF1,dodavanjeKnjige).addToBackStack(null).commit();

    }

    @Override
    public void onDodajKnjigu(String kategorija, Knjiga k) {

             Knjiga  knjiga = new Knjiga(k.getImeAutora(), k.getNaziv(), k.getAutori(), k.getOpis(), k.getDatumObjavljivanja(), k.getSlika(),k.getBrojStranica() );

       /* k.setImeAutora(k.getAutori().get(0).getImeiPrezime());
        k.setNazivKnjige(k.getNaziv());
        k.setKategorija(kategorija); */
        knjige.add(knjiga);
    }

    @Override
    public void onPreporuciClick(Knjiga k) {

        Bundle arg=new Bundle();
        arg.putParcelable("knjiga", k);
        FragmentPreporuci fragmentPreporuci =new FragmentPreporuci();
        fragmentPreporuci.setArguments(arg);

        getFragmentManager().beginTransaction().replace(R.id.mjestoF1,fragmentPreporuci).addToBackStack(null).commit();

    }
}
