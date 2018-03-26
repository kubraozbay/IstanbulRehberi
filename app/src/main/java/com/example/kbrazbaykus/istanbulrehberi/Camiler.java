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


public class Camiler extends Fragment {

    OzelAdaptor adaptor;
    FirebaseDatabase db;
    StorageReference depo;
    ListView list;
    Intent intent;
    ProgressBar pb;

    DatabaseReference okuma;

    public static String baslik,icerik;
    public static boolean camiler;
    final List<String> listeIsim=new ArrayList<String>();
    final List<String>listeResim=new ArrayList<>();

    private final String img_url[]={
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Ayasofya.jpg?alt=media&token=595838c6-75bd-407c-92fe-f9993aaedf24",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Ey%C3%BCp%20Sultan%20Camii.jpg?alt=media&token=26069610-986a-4680-b27e-91d7ff0769d7",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Ortak%C3%B6y%20Camii.jpg?alt=media&token=b003f992-c386-4148-9e11-a6f1143fdcd7",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Sultan%20Ahmet%20Camii.jpg?alt=media&token=5509405e-e5ec-428f-aec0-97d6b684778a",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Yeni%20Camii.jpg?alt=media&token=96f87a84-901c-4c5f-a070-b8be7d3ace63"
    };
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        camiler=true;
        Kiliseler.kiliseler=false;
        Saraylar.saraylar=false;
        Parklar.parklar=false;
        Kuleler.kuleler=false;
        Muzeler.muzeler=false;
        YoreselYemekler.cafe=false;
        Tatlicilar.tatlici=false;
        Balikci.balikci=false;
        Kahveciler.kahveci=false;
        AVM.avm=false;


        getActivity().setTitle(R.string.cami);

        list=(ListView)view.findViewById(R.id.listeCami);
         pb=(ProgressBar)view.findViewById(R.id.progressBar3);

       adaptor=new OzelAdaptor(this.getActivity(),listeIsim,listeResim);
        list.setAdapter(adaptor);
        for(int i=0;i<img_url.length;i++){
            listeResim.add(img_url[i].toString());
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                db = FirebaseDatabase.getInstance();
                DatabaseReference oku=FirebaseDatabase.getInstance().getReference();

               if(GirisSayfasi.turkceMi) okuma = oku.child("Camiler").child(list.getAdapter().getItem(position).toString());
                else okuma = oku.child("en-Camiler").child(list.getAdapter().getItem(position).toString());


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

        return inflater.inflate(R.layout.fragment_camiler, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        depo = FirebaseStorage.getInstance().getReference();
        db = FirebaseDatabase.getInstance();

        if(GirisSayfasi.turkceMi)
        okuma = db.getReference().child("Camiler");
        else
            okuma = db.getReference().child("en-Camiler");

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
