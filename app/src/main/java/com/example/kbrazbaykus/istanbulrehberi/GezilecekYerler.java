package com.example.kbrazbaykus.istanbulrehberi;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class GezilecekYerler extends Fragment implements View.OnClickListener{

    Button saray,kule,cami,kilise,park,muze;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_gezilecek_yerler, container, false);

        getActivity().setTitle(R.string.gezilecekYerler);


        saray=(Button)view.findViewById(R.id.saray);
        kule=(Button)view.findViewById(R.id.kule);
        muze=(Button)view.findViewById(R.id.muze);
        park=(Button)view.findViewById(R.id.park);
        kilise=(Button)view.findViewById(R.id.kilise);
        cami=(Button)view.findViewById(R.id.cami);

        saray.setOnClickListener(this);
        kule.setOnClickListener(this);
        muze.setOnClickListener(this);
        park.setOnClickListener(this);
        kilise.setOnClickListener(this);
        cami.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        Fragment fragment=null;
        switch (v.getId()){
            case R.id.saray:
                fragment=new Saraylar();
                break;
            case R.id.cami:
                fragment=new Camiler();
                break;
            case R.id.muze:
                fragment=new Muzeler();
                break;
            case R.id.kule:
                fragment=new Kuleler();
                break;
            case R.id.kilise:
                fragment=new Kiliseler();
                break;
            case R.id.park:
                fragment=new Parklar();
                break;

        }
        if(fragment!=null){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
  }
}
