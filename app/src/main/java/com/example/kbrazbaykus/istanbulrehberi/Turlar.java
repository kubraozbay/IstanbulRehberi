package com.example.kbrazbaykus.istanbulrehberi;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import me.relex.circleindicator.CircleIndicator;


public class Turlar extends Fragment {

    RadioGroup radioGroup;
    RadioButton birgun,ikigun,ucgun;
    AndroidImageAdapter adapter;
    String dilCifti="tr-en";

    String bilgi;
    String baslik;
    String icerik;
    TextView top,bottom;
    Button btn;
    ViewPager mViewPager;
    String rota="";
    String resultString;
     DatabaseReference myRef;
    CircleIndicator indicator;
    FirebaseDatabase db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_turlar, container, false);

        getActivity().setTitle(R.string.tur);

        birgun=(RadioButton)view.findViewById(R.id.birgun);
        ikigun=(RadioButton)view.findViewById(R.id.ikigun);
        ucgun=(RadioButton)view.findViewById(R.id.ucgun);
        radioGroup=(RadioGroup)view.findViewById(R.id.text);
        top=(TextView)view.findViewById(R.id.top);
        bottom=(TextView)view.findViewById(R.id.bottom);

        mViewPager=(ViewPager)view.findViewById(R.id.viewpager);

        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        btn=(Button)view.findViewById(R.id.haritadabul);
        radioGroup.clearCheck();

        radioGroup.check(R.id.birgun);

        bilgi="rota1";

        rota="https://www.google.com.tr/maps/dir//Topkap%C4%B1+Saray%C4%B1+M%C3%BCzesi,+Cankurtaran+Mh.,+34122+Fatih%2F%C4%B0stanbul/Ayasofya+M%C3%BCzesi,+Sultan+Ahmet+Mahallesi,+Ayasofya+Meydan%C4%B1,+34122+Fatih%2F%C4%B0stanbul/Sultan+Ahmet+Camii,+Sultan+Ahmet+Mahallesi,+Atmeydan%C4%B1+Cd.+No:7,+34122+Fatih%2F%C4%B0stanbul/@41.0221733,28.9574909,14z/data=!4m20!4m19!1m0!1m5!1m1!1s0x14cab9b8afa5f833:0x15aa1943c3015300!2m2!1d28.9833789!2d41.0115195!1m5!1m1!1s0x14cab9be92011c27:0x236e6f6f37444fae!2m2!1d28.980175!2d41.008583!1m5!1m1!1s0x14cab9bd6570f4e1:0xe52df7368a157ca4!2m2!1d28.9768138!2d41.0054096";
        db=FirebaseDatabase.getInstance();
        if(GirisSayfasi.turkceMi==true)
        myRef=db.getReference("Turlar").child("Topkapı Sarayı-Ayasofya Müzesi-Sultanahmet Camii");
        else
            myRef=db.getReference("Turlar").child("Topkapı Palace-Ayasofya Museum-Blue Mosque");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                top.setText(dataSnapshot.getKey().toString());
                bottom.setText(dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


     radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
               switch(radioGroup.getCheckedRadioButtonId()){
                   case R.id.birgun:
                       bilgi="rota1";
                       rota="https://www.google.com.tr/maps/dir//Topkap%C4%B1+Saray%C4%B1+M%C3%BCzesi,+Cankurtaran+Mh.,+34122+Fatih%2F%C4%B0stanbul/Ayasofya+M%C3%BCzesi,+Sultan+Ahmet+Mahallesi,+Ayasofya+Meydan%C4%B1,+34122+Fatih%2F%C4%B0stanbul/Sultan+Ahmet+Camii,+Sultan+Ahmet+Mahallesi,+Atmeydan%C4%B1+Cd.+No:7,+34122+Fatih%2F%C4%B0stanbul/@41.0221733,28.9574909,14z/data=!4m20!4m19!1m0!1m5!1m1!1s0x14cab9b8afa5f833:0x15aa1943c3015300!2m2!1d28.9833789!2d41.0115195!1m5!1m1!1s0x14cab9be92011c27:0x236e6f6f37444fae!2m2!1d28.980175!2d41.008583!1m5!1m1!1s0x14cab9bd6570f4e1:0xe52df7368a157ca4!2m2!1d28.9768138!2d41.0054096";
                       if(GirisSayfasi.turkceMi==true)
                           myRef=db.getReference("Turlar").child("Topkapı Sarayı-Ayasofya Müzesi-Sultanahmet Camii");
                       else
                           myRef=db.getReference("Turlar").child("Topkapı Palace-Ayasofya Museum-Blue Mosque");
                       myRef.addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {


                               top.setText(dataSnapshot.getKey().toString());
                               bottom.setText(dataSnapshot.getValue().toString());
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });


                       break;
                   case R.id.ikigun:
                       bilgi="rota2";
                       rota="https://www.google.com.tr/maps/dir//%C4%B0ETT+K%C4%B1z+Kulesi+Dura%C4%9F%C4%B1,+Salacak+Mahallesi,+%C3%9Csk%C3%BCdar+Harem+Sahil+Yolu,+34668+%C3%9Csk%C3%BCdar%2F%C4%B0stanbul/Beylerbeyi+Saray%C4%B1+TBMM+Milli+Saraylar,+Beylerbeyi+Mah,+Abdullah+A%C4%9Fa+Caddesi+Beylerbeyi+Saray%C4%B1,+34676+%C3%9Csk%C3%BCdar%2F%C4%B0stanbul/Kuzguncuk+Sahili,+Kuzguncuk+Mahallesi,+Kuzguncuk+%C3%87ar%C5%9F%C4%B1s%C4%B1+Cd.+No:43,+34674+%C3%9Csk%C3%BCdar%2F%C4%B0stanbul/@41.0278284,29.0071733,14z/data=!4m21!4m20!1m0!1m5!1m1!1s0x14cab828c4b5dc29:0x284b9fbd2563f35b!2m2!1d29.0089021!2d41.0191997!1m5!1m1!1s0x14cab7da02210c0f:0xaa4ea6c2e70ee70a!2m2!1d29.039887!2d41.0426736!1m5!1m1!1s0x14cab7c240f6ef29:0xe57debd7ee24515!2m2!1d29.0301118!2d41.037087!3e0";
                      if(GirisSayfasi.turkceMi==true)
                       myRef=db.getReference("Turlar").child("Kız Kulesi-Beylerbeyi Sarayı-Kuzguncuk Sahili");
                       else
                          myRef=db.getReference("Turlar").child("Madien's Tower-Beylerbeyi Palace-Kuzguncuk Seaside");
                       myRef.addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {


                               top.setText(dataSnapshot.getKey().toString());
                               bottom.setText(dataSnapshot.getValue().toString());
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });
                       break;
                   case R.id.ucgun:
                       bilgi="rota3";
                       rota="https://www.google.com.tr/maps/dir//Kapal%C4%B1+%C3%87ar%C5%9F%C4%B1,+Beyaz%C4%B1t+Mh.,+34126+Fatih%2F%C4%B0stanbul/M%C4%B1s%C4%B1r+%C3%87ar%C5%9F%C4%B1s%C4%B1,+R%C3%BCstem+Pa%C5%9Fa+Mahallesi,+Erzak+Ambar%C4%B1+Sok.+No:92,+34116+Fatih%2F%C4%B0stanbul/Emin%C3%B6n%C3%BC+%C4%B0skelesi,+Hobyar+Mahallesi,+34112+Emin%C3%B6n%C3%BC%2FFatih%2F%C4%B0stanbul/@41.0143456,28.9679385,16z/data=!4m21!4m20!1m0!1m5!1m1!1s0x14cab99162d70527:0x64c8680b5ac198ab!2m2!1d28.9680681!2d41.0106848!1m5!1m1!1s0x14cab9eb6f7cdb9f:0x44bbef8d97b0505d!2m2!1d28.9705194!2d41.0165001!1m5!1m1!1s0x14cab9ea139ad20d:0x4d8eb4a4292c37da!2m2!1d28.9750462!2d41.017411!3e0";
                       if(GirisSayfasi.turkceMi==true)
                       myRef=db.getReference("Turlar").child("Kapalı Çarşı-Mısır Çarşısı-Eminönü İskelesi");
                       else
                           myRef=db.getReference("Turlar").child("Grand Bazaar-Egyptian Bazaar-Eminönü Seaside");


                       myRef.addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                               top.setText(dataSnapshot.getKey().toString());
                               bottom.setText(dataSnapshot.getValue().toString());
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });
                       break;
               }


                AndroidImageAdapter adapterView = new AndroidImageAdapter(getActivity().getApplicationContext(),bilgi);
                mViewPager.setAdapter(adapterView);
                indicator.setViewPager(mViewPager);

            }
        });

        AndroidImageAdapter adapterView = new AndroidImageAdapter(getActivity().getApplicationContext(),bilgi);
        mViewPager.setAdapter(adapterView);
        indicator.setViewPager(mViewPager);

btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent navigation = new Intent(Intent.ACTION_VIEW,
                Uri.parse(rota));
        startActivity(navigation);
    }
});


        return view;
    }



}
