package com.example.amer.bibliotekarma;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Amer on 22.05.2018..
 */

public class DohvatiKnjige extends AsyncTask<String,Integer, Void> {


    private ArrayList<Knjiga> knjige=new ArrayList<>();

    public interface IDohvatiKnjigeDone {
        public void onDohvatiDone(ArrayList<Knjiga> knjige);
    }

    private IDohvatiKnjigeDone p;
    public DohvatiKnjige(IDohvatiKnjigeDone p) { this.p=p; }

    @Override
    protected Void doInBackground(String... params) {
        String[] split=params[0].split(";");
        for(int i=0;i<split.length;i++) {
            String query = null;
            try {
                query = URLEncoder.encode(split[i], "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url1 = "https://www.googleapis.com/books/v1/volumes?q="+query+"&maxResults=5";
            try {
                URL url = new URL(url1);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String rezultat = convertStreamToString(in);
                JSONObject jo = new JSONObject(rezultat);
                JSONArray items = jo.getJSONArray("items");
                for (int j = 0; j < items.length(); j++) {
                    JSONObject book = items.getJSONObject(j);
                    String id=null;
                    try {
                        id = book.getString("id");
                    } catch(JSONException e) {
                        id=" ";
                    }
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                    String naziv;
                    try {
                        naziv=volumeInfo.getString("title");
                    } catch(JSONException e) {
                        naziv=" ";
                    }
                    ArrayList<Autor> autori=new ArrayList<>();
                    JSONArray authors;
                    try {
                        authors=volumeInfo.getJSONArray("authors");
                        for (int k = 0; k < authors.length(); k++) autori.add(new Autor(authors.getString(k), id));
                    } catch(JSONException e) {
                        autori.add(new Autor(" ", id));
                    }
                    String opis;
                    try {
                        opis=volumeInfo.getString("description");
                    } catch(JSONException e) {
                        opis=" ";
                    }
                    String datum;
                    try {
                        datum=volumeInfo.getString("publishedDate");
                    } catch(JSONException e) {
                        datum=" ";
                    }
                    JSONObject image = null;
                    String urlSlika=null;
                    try {
                        image=volumeInfo.getJSONObject("imageLinks");
                        urlSlika=image.getString("smallThumbnail");
                    } catch(JSONException e) {
                        urlSlika="http://www.slatina.net/wp-content/uploads/2013/07/knjige.jpg";
                    }
                    URL slika = new URL(urlSlika);
                    int broj;
                    try {
                        broj=volumeInfo.getInt("pageCount");
                    } catch(JSONException e) {
                        broj=0;
                    }
                    knjige.add(new Knjiga(id,naziv,autori,opis,datum,slika,broj));
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }




    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        p.onDohvatiDone(knjige);
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line+"\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }
}
