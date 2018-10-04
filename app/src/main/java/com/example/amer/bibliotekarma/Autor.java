package com.example.amer.bibliotekarma;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Amer on 22.05.2018..
     */

public class Autor implements Serializable {
    private String imeiPrezime;
    private int brojKnjiga=1;
    ArrayList<String> knjige=new ArrayList<>();

    public Autor(String imeiPrezime) {
        this.imeiPrezime = imeiPrezime;
    }

    public String getImeiPrezime() {
        return imeiPrezime;
    }

    public void dodajKnjigu() {
        brojKnjiga++;
    }

    public void setImeiPrezime(String imeiPrezime) {
        this.imeiPrezime = imeiPrezime;
    }

    public ArrayList<String> getKnjige() {
        return knjige;
    }

    public void setKnjige(ArrayList<String> knjige) {
        this.knjige = knjige;
    }

    public Autor(String imeiPrezime, ArrayList<String> knjige) {
        this.imeiPrezime = imeiPrezime;
        this.knjige = knjige;
    }

    public Autor(String imeiPrezime, String id) {
        this.imeiPrezime=imeiPrezime;
        knjige.add(id);
    }

    public void dodajKnjigu(String id) {
        knjige.add(id);
    }

    public int dajBrojKnjiga() {
        return knjige.size();
    }
}
