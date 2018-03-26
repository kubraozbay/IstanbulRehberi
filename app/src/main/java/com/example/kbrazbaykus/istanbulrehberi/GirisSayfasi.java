package com.example.kbrazbaykus.istanbulrehberi;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import java.util.Locale;
import android.os.Handler;

public class GirisSayfasi extends AppCompatActivity {

    ImageButton english,turkce;
    public static boolean turkceMi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_giris_sayfasi);
        english= (ImageButton) findViewById(R.id.en);
        turkce = (ImageButton) findViewById(R.id.tr);

        english.setVisibility(View.INVISIBLE);
        turkce.setVisibility(View.INVISIBLE);



       Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                english.setVisibility(View.VISIBLE);
            }
        },1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                turkce.setVisibility(View.VISIBLE);
            }
        },1500);



        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              turkceMi=false;

                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());

                Intent intent=new Intent(GirisSayfasi.this,MainActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);


            }
        });

        turkce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               turkceMi=true;

                Locale locale = new Locale("tr");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                Intent intent=new Intent(GirisSayfasi.this,MainActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);



            }
        });
    }
}
