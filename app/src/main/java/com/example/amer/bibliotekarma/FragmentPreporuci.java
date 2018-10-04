package com.example.amer.bibliotekarma;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Amer on 04.06.2018..
 */

public class FragmentPreporuci extends Fragment {

    ArrayList<Knjiga> knjige = new ArrayList<>();
    ArrayList<String> kontakt = new ArrayList<>();
    ArrayList<String> mail = new ArrayList<>();
    Knjiga k;
    private Spinner spinnerkontakit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View iv = inflater.inflate(R.layout.fragment_preporuci,container,false);


        k=getArguments().getParcelable("knjiga");


        TextView Naziv,Autor,DatumObjavljivanja,Opis,BrojStranica;
        ImageView Slika;
        Naziv= iv.findViewById(R.id.fNaziv);
        Autor = iv.findViewById(R.id.fAutor);
        DatumObjavljivanja = iv.findViewById(R.id.fDatumObjavljivanja);
        Opis = iv.findViewById(R.id.fOpis);
        BrojStranica = iv.findViewById(R.id.fBrojStranica);
        Slika = iv.findViewById(R.id.fNaslovna);
        Naziv.setText(k.getNazivKnjige());
        Autor.setText(k.getAutori().get(0).getImeiPrezime());
        DatumObjavljivanja.setText(k.getDatumObjavljivanja());
        Opis.setText(k.getOpis());
        BrojStranica.setText(String.valueOf(k.getBrojStranica()));
        Picasso.with(getActivity()).load(String.valueOf(k.getSlika())).into(Slika);




        return iv;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            ContentResolver contentResolver = getActivity().getContentResolver();
            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
            if(cursor.getCount()>0){
                while(cursor.moveToNext()){
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor cursor1 = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (cursor1.moveToNext()){
                        String ime = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String email = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        if(email!=null){
                            kontakt.add(ime);
                            mail.add(email);
                        }
                    }
                    cursor1.close();
                }
            }
            cursor.close();

            spinnerkontakit = (Spinner)getView().findViewById(R.id.sKontakti);
        ArrayAdapter<String> adapter; adapter =new ArrayAdapter<>(getActivity(),android.R.layout.simple_expandable_list_item_1,mail);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerkontakit.setAdapter(adapter);

        Button dPosalji;

        dPosalji = (Button)getView().findViewById(R.id.dPosalji);
        dPosalji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView naziv = (TextView) getView().findViewById(R.id.fNaziv);
                TextView autor = (TextView) getView().findViewById(R.id.fAutor);

                Log.i("Send email", "");
                String[] TO = {spinnerkontakit.getSelectedItem().toString()};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Preporuka za knjigu");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Zdravo " + kontakt.get(spinnerkontakit.getSelectedItemPosition()) + ", \nPročitaj knjigu " + k.getNaziv() + " od " + k.getAutori().get(0).getImeiPrezime() + "!");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    Log.i("Finished sending email", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
