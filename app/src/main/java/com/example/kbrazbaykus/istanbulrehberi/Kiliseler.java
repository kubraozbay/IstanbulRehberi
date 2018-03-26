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


public class Kiliseler extends Fragment {

    OzelAdaptor adaptor;
    FirebaseDatabase db;
    StorageReference depo;
    ListView list;
    Intent intent;
    DatabaseReference okuma;
    public static String baslik,icerik;

    ProgressBar pb;
    public static boolean kiliseler;
    final List<String> listeIsim=new ArrayList<String>();
    final List<String>listeResim=new ArrayList<>();
    private final String img_url[]={
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Aya%20%C4%B0rini%20Kilisesi.jpg?alt=media&token=0ab17d5d-1be3-4a02-a977-af7f9d3fcc4d",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Fener%20Rum%20Patrikhanesi.jpg?alt=media&token=29a5205c-efcb-4ff2-bd2a-ae500c25b988",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Saint%20Peter%20ve%20Saint%20Paul%20Kilisesi.jpg?alt=media&token=256db022-333f-4e99-a869-c6453a81db66",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/St%20Antuan%20Katolik%20Kilisesi.jpg?alt=media&token=1f6224ff-0885-486c-8c08-2da277995c0a"
    };
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Camiler.camiler=false;
        kiliseler=true;
        Saraylar.saraylar=false;
        Parklar.parklar=false;
        Kuleler.kuleler=false;
        Muzeler.muzeler=false;
        YoreselYemekler.cafe=false;
        Tatlicilar.tatlici=false;
        Balikci.balikci=false;
        Kahveciler.kahveci=false;
        AVM.avm=false;


        getActivity().setTitle(R.string.kilise);
        pb=(ProgressBar)view.findViewById(R.id.progressBar3);
        list=(ListView)view.findViewById(R.id.listeKilise);
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
                    okuma = oku.child("Kiliseler").child(list.getAdapter().getItem(position).toString());
                else
                    okuma = oku.child("en-Kiliseler").child(list.getAdapter().getItem(position).toString());

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

        return inflater.inflate(R.layout.fragment_kiliseler, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        depo = FirebaseStorage.getInstance().getReference();
        db = FirebaseDatabase.getInstance();
       if(GirisSayfasi.turkceMi)
        okuma = db.getReference().child("Kiliseler");
        else
           okuma = db.getReference().child("en-Kiliseler");

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
