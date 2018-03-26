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


public class Parklar extends Fragment {

    OzelAdaptor adaptor;
    FirebaseDatabase db;
    StorageReference depo;
    ListView list;
    DatabaseReference okuma;
    Intent intent;
    ProgressBar pb;

    public static String baslikadi,icerikadi;
    public static boolean parklar;
    final List<String> listePark=new ArrayList<String>();
    final List<String>listeResim=new ArrayList<>();

    private final String img_url[]={
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Beykoz%20Korusu.jpg?alt=media&token=fda64a22-404e-40c0-b722-4dfd94e4b428",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Emirgan%20Korusu.jpg?alt=media&token=80fac2dd-1c8d-4bf9-ad5a-5aedd97045cd",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Fethipa%C5%9Fa%20Korusu.jpg?alt=media&token=7266a756-a927-49cd-a523-2b1ef5d30e7a",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/G%C3%BClhane%20Park%C4%B1.jpg?alt=media&token=fc1ebebd-d996-4387-a90f-73fa0a74660c",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Y%C4%B1ld%C4%B1z%20Park%C4%B1.jpg?alt=media&token=15fa8518-24e7-49ac-933a-b48b3f63975f",
    };
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Camiler.camiler=false;
        Kiliseler.kiliseler=false;
        Saraylar.saraylar=false;
        parklar=true;
        Kuleler.kuleler=false;
        Muzeler.muzeler=false;
        YoreselYemekler.cafe=false;
        Tatlicilar.tatlici=false;
        Balikci.balikci=false;
        Kahveciler.kahveci=false;
        AVM.avm=false;

        getActivity().setTitle(R.string.park);

        for(int i=0;i<img_url.length;i++){
            listeResim.add(img_url[i].toString());
        }

        pb=(ProgressBar)view.findViewById(R.id.progressBar3);
        list=(ListView)view.findViewById(R.id.listePark);
        adaptor=new OzelAdaptor(this.getActivity(),listePark,listeResim);
        list.setAdapter(adaptor);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                db = FirebaseDatabase.getInstance();
                DatabaseReference oku=FirebaseDatabase.getInstance().getReference();

                if(GirisSayfasi.turkceMi)
                 okuma = oku.child("Parklar").child(list.getAdapter().getItem(position).toString());
                else
                    okuma = oku.child("en-Parklar").child(list.getAdapter().getItem(position).toString());

                baslikadi=list.getAdapter().getItem(position).toString();

                okuma.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        icerikadi=dataSnapshot.getValue().toString();

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
       return inflater.inflate(R.layout.fragment_parklar, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        depo = FirebaseStorage.getInstance().getReference();
        db = FirebaseDatabase.getInstance();

        if(GirisSayfasi.turkceMi)
            okuma = db.getReference().child("Parklar");
        else
            okuma = db.getReference().child("en-Parklar");

        okuma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot key : dataSnapshot.getChildren()) {

                    listePark.add(key.getKey().toString());
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
