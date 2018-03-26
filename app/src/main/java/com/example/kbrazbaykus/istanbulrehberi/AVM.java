package com.example.kbrazbaykus.istanbulrehberi;

import android.content.Context;
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


public class AVM extends Fragment {
    OzelAdaptor adaptor;
    FirebaseDatabase db;
    StorageReference depo;
    ListView list;
    Intent intent;
    ProgressBar pb;
    public static String baslik,icerik;
    public static boolean avm;
    DatabaseReference okuma;
    final List<String> listeIsim=new ArrayList<String>();
    final List<String>listeResim=new ArrayList<>();
    private String img_url[]={
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Aqua%20Florya%20Al%C4%B1%C5%9Fveri%C5%9F%20Merkezi.jpg?alt=media&token=9955aeb5-adcd-4849-af79-b895e73136ee",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Zorlu%20Center.jpg?alt=media&token=948845f9-6d66-4dbd-b11c-be54168e0d55",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/%C4%B0stanbul%20Cevahir%20Al%C4%B1%C5%9Fveri%C5%9F%20ve%20E%C4%9Flence%20Merkezi.jpg?alt=media&token=6d7463af-015d-4115-8b71-dc95c9b9cb05",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/%C4%B0stinye%20Park%20Al%C4%B1%C5%9Fveri%C5%9F%20Merkezi.jpg?alt=media&token=0a06f126-5d4b-4fb2-b3da-48a737abd0ab"
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
        YoreselYemekler.cafe=false;
        avm=true;
        Tatlicilar.tatlici=false;
        Balikci.balikci=false;
        Kahveciler.kahveci=false;

        for(int i=0;i<img_url.length;i++){
            listeResim.add(img_url[i].toString());
        }

        getActivity().setTitle(R.string.avm);
        pb=(ProgressBar)view.findViewById(R.id.progressBar3);
        list=(ListView)view.findViewById(R.id.listeAVM);
        adaptor=new OzelAdaptor(this.getActivity(),listeIsim,listeResim);
        list.setAdapter(adaptor);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                db = FirebaseDatabase.getInstance();
                DatabaseReference oku=FirebaseDatabase.getInstance().getReference();


                    okuma = oku.child("AVMler").child(list.getAdapter().getItem(position).toString());


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        depo = FirebaseStorage.getInstance().getReference();
        db = FirebaseDatabase.getInstance();

            okuma = db.getReference().child("AVMler");


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_avm, container, false);
    }


}
