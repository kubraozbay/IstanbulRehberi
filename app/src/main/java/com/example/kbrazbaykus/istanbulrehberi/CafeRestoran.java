package com.example.kbrazbaykus.istanbulrehberi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class CafeRestoran extends Fragment implements View.OnClickListener{

    Button yoresel,tatlıcı,balikci,kahveci;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_cafe_restoran, container, false);

        getActivity().setTitle("Cafe&Restoran");

        yoresel=(Button)view.findViewById(R.id.yoresel);
        tatlıcı=(Button)view.findViewById(R.id.tatlici);
        balikci=(Button)view.findViewById(R.id.balikci);
        kahveci=(Button)view.findViewById(R.id.kahveci);

        yoresel.setOnClickListener(this);
        tatlıcı.setOnClickListener(this);
        balikci.setOnClickListener(this);
        kahveci.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {
        Fragment fragment=null;
        switch (v.getId()){
            case R.id.yoresel:
                fragment=new YoreselYemekler();
                break;
            case R.id.tatlici:
                fragment=new Tatlicilar();
                break;
            case R.id.balikci:
                fragment=new Balikci();
                break;
            case R.id.kahveci:
                fragment=new Kahveciler();
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
