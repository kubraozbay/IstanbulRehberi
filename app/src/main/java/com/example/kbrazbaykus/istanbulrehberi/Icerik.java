package com.example.kbrazbaykus.istanbulrehberi;


import android.content.Intent;

import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Icerik extends AppCompatActivity {


    TextView b, i,giris,saat;
    Button harita;
    ImageView img;
    String nerdeyiz;
    DatabaseReference konum;


    public void KonumBul(String neresi){

        if(GirisSayfasi.turkceMi==true||AVM.avm==true||Tatlicilar.tatlici==true||Kahveciler.kahveci==true||Balikci.balikci==true
                ||YoreselYemekler.cafe==true)
            konum=FirebaseDatabase.getInstance().getReference("Konum");
        else konum=FirebaseDatabase.getInstance().getReference("en-Konum");


        DatabaseReference konumAl=konum.child(neresi);
        konumAl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nerdeyiz = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icerik);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b = (TextView) findViewById(R.id.sarayBaslik);
        i = (TextView) findViewById(R.id.onem);
        img = (ImageView) findViewById(R.id.yerResimleri);
        giris=(TextView)findViewById(R.id.giris);
        saat=(TextView)findViewById(R.id.saat);

        giris.setVisibility(View.GONE);
        saat.setVisibility(View.GONE);

        harita = (Button) findViewById(R.id.harita);

            harita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent navigation = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=&daddr="+nerdeyiz));
                    startActivity(navigation);
                }

            });


        if(Saraylar.saraylar==true){

            b.setText(Saraylar.baslikadi) ;
            i.setText(Saraylar.icerikadi) ;
            KonumBul(Saraylar.baslikadi);
            this.setTitle(Saraylar.baslikadi);

            //giris.setVisibility(View.VISIBLE);
            //saat.setVisibility(View.VISIBLE);

           // giris.setText(getString(R.string.giris)+" 5 TL");
           // saat.setText(getString(R.string.saat)+" 09.00-16.00");
        }
        else if(Parklar.parklar==true){
            b.setText(Parklar.baslikadi) ;
            i.setText(Parklar.icerikadi) ;
            KonumBul(Parklar.baslikadi);
            this.setTitle(Parklar.baslikadi);
        }
        else if(Camiler.camiler==true){
            b.setText(Camiler.baslik) ;
            i.setText(Camiler.icerik) ;
            KonumBul(Camiler.baslik);
            this.setTitle(Camiler.baslik);
        }
        else if(Kiliseler.kiliseler==true)
        {
            b.setText(Kiliseler.baslik) ;
            i.setText(Kiliseler.icerik) ;
            KonumBul(Kiliseler.baslik);
            this.setTitle(Kiliseler.baslik);
        }
        else if(Kuleler.kuleler==true){
            b.setText(Kuleler.baslik) ;
            i.setText(Kuleler.icerik) ;
            KonumBul(Kuleler.baslik);
            this.setTitle(Kuleler.baslik);
        }
        else if(Muzeler.muzeler==true){
            b.setText(Muzeler.baslik) ;
            i.setText(Muzeler.icerik) ;
            KonumBul(Muzeler.baslik);
            this.setTitle(Muzeler.baslik);
//            giris.setVisibility(View.VISIBLE);
//            saat.setVisibility(View.VISIBLE);
//
//            giris.setText(getString(R.string.giris)+" 5 TL");
//            saat.setText(getString(R.string.saat)+" 09.00-16.00");
        }
        else if(YoreselYemekler.cafe==true){
            b.setText(YoreselYemekler.baslik) ;
            i.setText(YoreselYemekler.icerik) ;
            KonumBul(YoreselYemekler.baslik);
            this.setTitle(YoreselYemekler.baslik);
        }
        else if(Tatlicilar.tatlici==true){
            b.setText(Tatlicilar.baslik);
            i.setText(Tatlicilar.icerik);
            KonumBul(Tatlicilar.baslik);
            this.setTitle(Tatlicilar.baslik);

        }
        else if(Balikci.balikci==true){
            b.setText(Balikci.baslik);
            i.setText(Balikci.icerik);
            KonumBul(Balikci.baslik);
            this.setTitle(Balikci.baslik);
        }
        else if(AVM.avm==true){
            b.setText(AVM.baslik) ;
            i.setText(AVM.icerik) ;
            KonumBul(AVM.baslik);
            this.setTitle(AVM.baslik);
        }
        else if(Kahveciler.kahveci==true){
            b.setText(Kahveciler.baslik) ;
            i.setText(Kahveciler.icerik) ;
            KonumBul(Kahveciler.baslik);
            this.setTitle(Kahveciler.baslik);
        }

        Bundle extras=getIntent().getExtras();
        String url=extras.getString("resimURL");
        Glide.with(this).load(url).into(img);

    }
    Intent intent;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
           if(Saraylar.saraylar==true){

               intent=new Intent(this,Saraylar.class);
           }
           else if(Parklar.parklar==true){
               intent=new Intent(this,Parklar.class);
           }
           else if(Camiler.camiler==true){
               intent=new Intent(this,Camiler.class);
           }
           else if(Kiliseler.kiliseler==true)
           {
               intent=new Intent(this,Kiliseler.class);
           }
           else if(Kuleler.kuleler==true){
               intent=new Intent(this,Kuleler.class);
           }
           else if(Muzeler.muzeler==true){
               intent=new Intent(this,Muzeler.class);
           }
           else if(YoreselYemekler.cafe==true){
               intent=new Intent(this,YoreselYemekler.class);
           }


            NavUtils.navigateUpTo(this,intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
