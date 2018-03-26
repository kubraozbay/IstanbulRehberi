package com.example.kbrazbaykus.istanbulrehberi;

import android.app.Activity;
import android.content.Context;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kübra Özbaykus on 18.08.2017.
 */

public class OzelAdaptor extends BaseAdapter {

    TextView sarayIsmi;
    ImageView imageView;

    String url;

    private LayoutInflater mInflater;
    private List<String> isimListesi;
    private List<String>resimListesi;


    public OzelAdaptor(Activity activity, List<String> isimler,List<String>resimler) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        isimListesi = isimler;
        resimListesi=resimler;

    }

    @Override
    public int getCount() {
        return isimListesi.size();
    }

    @Override
    public String getItem(int position) {
        return isimListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View satirView;
        satirView = mInflater.inflate(R.layout.satirlar,null);
        sarayIsmi = (TextView) satirView.findViewById(R.id.sarayIsim);

        imageView = (ImageView) satirView.findViewById(R.id.sarayResim);

        String isim = isimListesi.get(position);
        sarayIsmi.setText(isim);


        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageReference=storage.getReference().child(isim+".jpg");

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url=uri.toString();

            }
        });


      Glide.with(mInflater.getContext()).load(resimListesi.get(position).toString()).into(imageView);

        return satirView;
    }


}