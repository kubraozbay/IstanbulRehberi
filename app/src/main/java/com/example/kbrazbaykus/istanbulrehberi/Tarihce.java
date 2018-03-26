package com.example.kbrazbaykus.istanbulrehberi;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



public class Tarihce extends Fragment{


    FirebaseDatabase db;
    StorageReference depo;
    TextView baslik;
    TextView icerik;
    ImageView imageView;
    DatabaseReference okuma;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.istanbulRehberim);
        View view = inflater.inflate(R.layout.fragment_ana_sayfa, container,false);
        baslik=(TextView)view.findViewById(R.id.baslik);
        icerik=(TextView)view.findViewById(R.id.icerik);
        imageView=(ImageView)view.findViewById(R.id.imageView2);

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/tarih.jpg?alt=media&token=66c3e7e3-8294-4403-a7f5-6fd11e102216").into(imageView);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depo = FirebaseStorage.getInstance().getReference();
        db = FirebaseDatabase.getInstance();




    if(GirisSayfasi.turkceMi) {

      okuma = db.getReference("istanbulTarihi");
    }
    else{
       okuma = db.getReference("en-istanbulTarihi");

    }

    okuma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                baslik.setText("");
                icerik.setText("");

                Iterable<DataSnapshot> keys = dataSnapshot.getChildren();
                for (DataSnapshot key : keys) {

                    baslik.append(key.getKey().toString() + "\n");
                    icerik.append(key.getValue().toString() + "\n");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}
