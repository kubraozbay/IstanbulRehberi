package com.example.kbrazbaykus.istanbulrehberi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class IlkSayfa extends Fragment implements View.OnClickListener {


    Button btn1,btn2,btn3,btn4;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_ilk_sayfa, container, false);
        getActivity().setTitle(R.string.istanbulRehberim);

        btn1=(Button)view.findViewById(R.id.gezilecek);
        btn2=(Button)view.findViewById(R.id.cafe);
        btn3=(Button)view.findViewById(R.id.avmler);
        btn4=(Button)view.findViewById(R.id.tur);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {

        Fragment fragment=null;
        switch (v.getId())
        {
            case R.id.gezilecek:
                fragment=new GezilecekYerler();
                break;
            case R.id.cafe:
                fragment=new CafeRestoran();
                break;
            case R.id.avmler:
                fragment=new AVM();
                break;
            case R.id.tur:
                fragment=new Turlar();
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
