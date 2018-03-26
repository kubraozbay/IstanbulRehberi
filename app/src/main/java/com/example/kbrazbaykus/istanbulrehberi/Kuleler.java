package com.example.kbrazbaykus.istanbulrehberi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Kuleler extends Fragment {
    OzelAdaptor adaptor;
    FirebaseDatabase db;
    StorageReference depo;
    ListView list;
    Intent intent;
    ProgressBar pb;
    public static String baslik,icerik;
    public static boolean kuleler;
    DatabaseReference okuma;
    final List<String> listeIsim=new ArrayList<String>();
    final List<String>listeResim=new ArrayList<>();
    private final String img_url[]={
         "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Beyaz%C4%B1t%20Kulesi.jpg?alt=media&token=686ec61b-ebd0-4cb1-94d6-ad4eb0aa4cb7",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Dolmabah%C3%A7e%20Saat%20Kulesi.jpg?alt=media&token=abeccfe3-1703-4473-8eb2-9d7d8411faad",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Galata%20Kulesi.jpg?alt=media&token=eb297ba2-bb88-4226-acf6-bd3cc084c337",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/K%C4%B1z%20Kulesi.jpg?alt=media&token=40010683-2c62-4f73-9e27-b1cb4078a1bb",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Y%C4%B1ld%C4%B1z%20Saat%20Kulesi.jpg?alt=media&token=6fcbce79-32ec-4865-8dc1-03203b2c1441"
    };
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Camiler.camiler=false;
        Kiliseler.kiliseler=false;
        Saraylar.saraylar=false;
        Parklar.parklar=false;
        kuleler=true;
        Muzeler.muzeler=false;
        YoreselYemekler.cafe=false;
        Tatlicilar.tatlici=false;
        Balikci.balikci=false;
        Kahveciler.kahveci=false;
        AVM.avm=false;

        getActivity().setTitle(R.string.kule);

        pb=(ProgressBar)view.findViewById(R.id.progressBar3);
        for(int i=0;i<img_url.length;i++){
            listeResim.add(img_url[i].toString());
        }
        list=(ListView)view.findViewById(R.id.listeKule);
       adaptor=new OzelAdaptor(this.getActivity(),listeIsim,listeResim);
        list.setAdapter(adaptor);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                db = FirebaseDatabase.getInstance();
                DatabaseReference oku=FirebaseDatabase.getInstance().getReference();

                if(GirisSayfasi.turkceMi)
               okuma = oku.child("Kuleler").child(list.getAdapter().getItem(position).toString());
                else
                    okuma = oku.child("en-Kuleler").child(list.getAdapter().getItem(position).toString());

                baslik=list.getAdapter().getItem(position).toString();
                okuma.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        icerik=dataSnapshot.getValue().toString();

                        adaptor.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                FirebaseStorage storage=FirebaseStorage.getInstance();
                StorageReference storageReference=storage.getReference().child(list.getAdapter().getItem(position).toString()+".jpg");

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        intent=new Intent(getActivity().getApplicationContext(),Icerik.class);
                        intent.putExtra("resimURL",uri.toString());

                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    }
                });

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_kuleler, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        depo = FirebaseStorage.getInstance().getReference();
        db = FirebaseDatabase.getInstance();
       if(GirisSayfasi.turkceMi)
        okuma = db.getReference().child("Kuleler");
        else
           okuma = db.getReference().child("en-Kuleler");

        okuma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot key : dataSnapshot.getChildren()) {

                    listeIsim.add(key.getKey().toString());
                }
                pb.setVisibility(View.INVISIBLE);
                adaptor.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
