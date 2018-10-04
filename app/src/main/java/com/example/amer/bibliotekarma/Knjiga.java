package com.example.amer.bibliotekarma;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.PushbackInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Amer on 31.03.2018..
 */

public class Knjiga  implements Parcelable {
    private String imeAutora;
    private String nazivKnjige;
    private String kategorija;
    private int slikaInt;
    private String selektovanaBoja;

    private String id;
    private String naziv;
    private ArrayList<Autor> autori=new ArrayList<>();
    private String opis;
    private String datumObjavljivanja;
    private URL slika;
    private int brojStranica;

    public Knjiga() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public ArrayList<Autor> getAutori() {
        return autori;
    }

    public void setAutori(ArrayList<Autor> autori) {
        this.autori = autori;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getDatumObjavljivanja() {
        return datumObjavljivanja;
    }

    public void setDatumObjavljivanja(String datumObjavljivanja) {
        this.datumObjavljivanja = datumObjavljivanja;
    }

    public URL getSlika() {
        return slika;
    }

    public void setSlika(URL slika) {
        this.slika = slika;
    }

    public int getBrojStranica() {
        return brojStranica;
    }

    public void setBrojStranica(int brojStranica) {
        this.brojStranica = brojStranica;
    }

    public Knjiga(String id, String naziv, ArrayList<Autor> autori, String opis, String datumObjavljivanja, URL slika, int brojStranica) {
        this.id = id;
        this.naziv = naziv;
        this.autori = autori;
        this.opis = opis;
        this.datumObjavljivanja = datumObjavljivanja;
        this.slika = slika;
        this.brojStranica = brojStranica;
    }

    Knjiga(String imeAutora, String nazivKnjige, String kategorija, String selektovanaBoja) {
        this.imeAutora = imeAutora;
        this.nazivKnjige = nazivKnjige;
        this.kategorija = kategorija;
        this.selektovanaBoja = selektovanaBoja;
    }

    public static final Creator<Knjiga> CREATOR = new Creator<Knjiga>() {
        @Override
        public Knjiga createFromParcel(Parcel in){
            return new Knjiga(in);
        }
        @Override
        public Knjiga[] newArray(int size) {
            return new Knjiga[size];
        }
    };



    public String toString()
    {
        return kategorija;
    }


    public String getImeAutora() {
        return imeAutora;
    }

    public void setImeAutora(String imeAutora) {
        this.imeAutora = imeAutora;
    }

    public String getNazivKnjige() {
        return nazivKnjige;
    }

    public void setNazivKnjige(String nazivKnjige) {
        this.nazivKnjige = nazivKnjige;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public int getSlikaInt() {
        return slikaInt;
    }

    public void setSlikaInt(int slikaInt) {
        this.slikaInt = slikaInt;
    }

    public String getSelektovanaBoja() {
        return selektovanaBoja;
    }

    public void setSelektovanaBoja(String selektovanaBoja) {
        this.selektovanaBoja = selektovanaBoja;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imeAutora);
        parcel.writeString(nazivKnjige);
        parcel.writeString(kategorija);
        parcel.writeString(selektovanaBoja);
    }

    Knjiga(Parcel in)
    {
        imeAutora= in.readString();
        nazivKnjige = in.readString();
        kategorija = in.readString();
        selektovanaBoja = in.readString();
    }

}
