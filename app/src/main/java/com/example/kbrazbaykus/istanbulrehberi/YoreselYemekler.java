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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class YoreselYemekler extends Fragment {
    public static boolean cafe;
    OzelAdaptor adaptor;
    ListView list;
    FirebaseDatabase db;
    DatabaseReference okuma;
    public static String baslik,icerik;
    Intent intent;
    final List<String> listeIsim=new ArrayList<String>();
    final List<String>listeResim=new ArrayList<>();

    private String img_url[]={
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Asitane%20Restaurant.jpg?alt=media&token=9dbc0cda-4d26-49c6-988c-c7e9c0c98a2a",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Galata%20Kiva.jpg?alt=media&token=d9022ecd-3b4d-47b4-b823-49ae489e59df",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Hac%C4%B1%20Abdullah.jpg?alt=media&token=575ce0db-1743-4d71-a685-6ee1ec38a1d7"
    };
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Camiler.camiler=false;
        Kiliseler.kiliseler=false;
        Saraylar.saraylar=false;
        Parklar.parklar=false;
        Kuleler.kuleler=false;
        Muzeler.muzeler=false;
        cafe=true;
        Tatlicilar.tatlici=false;
        Balikci.balikci=false;
        Kahveciler.kahveci=false;
        AVM.avm=false;

        getActivity().setTitle(R.string.yoresel);

        for(int i=0;i<img_url.length;i++){
            listeResim.add(img_url[i].toString());
        }
        list=(ListView)view.findViewById(R.id.listeCafe);

        adaptor=new OzelAdaptor(this.getActivity(),listeIsim,listeResim);
        list.setAdapter(adaptor);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                db = FirebaseDatabase.getInstance();
                DatabaseReference oku=FirebaseDatabase.getInstance().getReference();


                    okuma = oku.child("YöreselYemekler").child(list.getAdapter().getItem(position).toString());

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
                    }
                });

            }
        });
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference oku;

        FirebaseDatabase db = FirebaseDatabase.getInstance();

            oku = db.getReference().child("YöreselYemekler");

        oku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot key : dataSnapshot.getChildren()) {
                    listeIsim.add(key.getKey().toString());
                }
                adaptor.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_yoresel_yemekler, container, false);
    }




}
