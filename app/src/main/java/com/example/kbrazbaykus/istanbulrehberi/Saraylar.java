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

public class Saraylar extends Fragment {
    OzelAdaptor adaptor;
    ListView list;
    Intent intent;
    public static String baslikadi,icerikadi;
    public static boolean saraylar;
    ProgressBar pb;

    final List<String> listeIsim=new ArrayList<String>();

    final List<String>listeResim=new ArrayList<String>();

    private final String img_url[]={
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Dolmabah%C3%A7e%20Saray%C4%B1.jpg?alt=media&token=7eaf7e6f-878e-479e-9f65-bb6961008c50",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/K%C3%BC%C3%A7%C3%BCksu%20Saray%C4%B1.jpg?alt=media&token=998a6698-277b-4c12-901f-07dea0d652aa",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Topkap%C4%B1%20saray%C4%B1.jpg?alt=media&token=d113ed2f-2e3d-483d-aae6-702bd7d8444d",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Y%C4%B1ld%C4%B1z%20Saray%C4%B1.jpg?alt=media&token=6c2de2d9-7814-459c-be15-91f6a6ca65a0",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/%C3%87%C4%B1ra%C4%9Fan%20Saray%C4%B1.jpg?alt=media&token=06fb6b2f-256a-49a8-88a9-49758d082f0d"
    };
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Camiler.camiler=false;
        Kiliseler.kiliseler=false;
        saraylar=true;
        Parklar.parklar=false;
        Kuleler.kuleler=false;
        Muzeler.muzeler=false;
        AVM.avm=false;
        YoreselYemekler.cafe=false;
        Tatlicilar.tatlici=false;
        Balikci.balikci=false;
        Kahveciler.kahveci=false;

        getActivity().setTitle(R.string.saray);

        list=(ListView)view.findViewById(R.id.liste);
        pb=(ProgressBar)view.findViewById(R.id.progressBar3);

        adaptor=new OzelAdaptor(this.getActivity(),listeIsim,listeResim);
        list.setAdapter(adaptor);

        for(int i=0;i<img_url.length;i++){
            listeResim.add(img_url[i].toString());
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                DatabaseReference okuma1;
                DatabaseReference oku=FirebaseDatabase.getInstance().getReference();

                if(GirisSayfasi.turkceMi)
                 okuma1 = oku.child("Saraylar").child(list.getAdapter().getItem(position).toString());
                else
                    okuma1 = oku.child("en-Saraylar").child(list.getAdapter().getItem(position).toString());

               baslikadi=list.getAdapter().getItem(position).toString();

                okuma1.addValueEventListener(new ValueEventListener() {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference oku;

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        if(GirisSayfasi.turkceMi)
            oku = db.getReference().child("Saraylar");
        else
            oku = db.getReference().child("en-Saraylar");

        oku.addValueEventListener(new ValueEventListener() {
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

        View view=inflater.inflate(R.layout.fragment_saraylar, container, false);
        return view ;
    }


}
