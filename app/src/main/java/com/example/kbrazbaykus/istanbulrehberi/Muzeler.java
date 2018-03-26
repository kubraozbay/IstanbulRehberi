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

import java.util.ArrayList;
import java.util.List;

public class Muzeler extends Fragment {
    OzelAdaptor adaptor;
    FirebaseDatabase db;
    StorageReference depo;
    ListView list;
    Intent intent;
    DatabaseReference okuma;
    ProgressBar pb;

    public static String baslik,icerik;
    final List<String> listeIsim=new ArrayList<String>();
    final List<String>listeResim=new ArrayList<>();

    public static boolean muzeler;

    private final String img_url[]={
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Masumiyet%20M%C3%BCzesi.jpg?alt=media&token=acd85aaa-b4f6-415c-bad4-59629febeb8d",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Oyuncak%20M%C3%BCzesi.jpg?alt=media&token=871daf11-02ce-43a4-a2d6-1ec781754301",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Pera%20M%C3%BCzesi.jpg?alt=media&token=3841b924-b739-4994-800b-5a96951d6432",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Rahmi%20Ko%C3%A7%20M%C3%BCzesi.jpg?alt=media&token=59e89709-ff77-4300-829c-80eb1729228e",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Sak%C4%B1p%20Sabanc%C4%B1%20M%C3%BCzesi.jpg?alt=media&token=236e4a4c-678c-4b1b-8e52-affc7025d9f1"
    };
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Camiler.camiler=false;
        Kiliseler.kiliseler=false;
        Saraylar.saraylar=false;
        Parklar.parklar=false;
        Kuleler.kuleler=false;
        muzeler=true;
        YoreselYemekler.cafe=false;
        Tatlicilar.tatlici=false;
        Balikci.balikci=false;
        Kahveciler.kahveci=false;
        AVM.avm=false;

        getActivity().setTitle(R.string.müze);

        pb=(ProgressBar)view.findViewById(R.id.progressBar3);
        list=(ListView)view.findViewById(R.id.listeMuze);
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
                if(GirisSayfasi.turkceMi)
                 okuma = oku.child("Müzeler").child(list.getAdapter().getItem(position).toString());
                else
                    okuma = oku.child("en-Müzeler").child(list.getAdapter().getItem(position).toString());

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

        return inflater.inflate(R.layout.fragment_muzeler, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        depo = FirebaseStorage.getInstance().getReference();
        db = FirebaseDatabase.getInstance();
        if(GirisSayfasi.turkceMi)
        okuma = db.getReference().child("Müzeler");
        else
            okuma = db.getReference().child("en-Müzeler");
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
