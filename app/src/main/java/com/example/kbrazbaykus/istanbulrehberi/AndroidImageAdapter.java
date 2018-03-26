package com.example.kbrazbaykus.istanbulrehberi;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Kübra Özbaykus on 30.09.2017.
 */

public class AndroidImageAdapter extends PagerAdapter {

    Context mContext;
    String gun;

    AndroidImageAdapter(Context context,String bilgi) {
        this.mContext = context;
        this.gun=bilgi;
    }

    @Override
    public int getCount() {
        return rota1.length;
    }


    private String[] rota1=new String[]{
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Topkap%C4%B1%20saray%C4%B1.jpg?alt=media&token=d113ed2f-2e3d-483d-aae6-702bd7d8444d",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Ayasofya.jpg?alt=media&token=595838c6-75bd-407c-92fe-f9993aaedf24",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Sultan%20Ahmet%20Camii.jpg?alt=media&token=5509405e-e5ec-428f-aec0-97d6b684778a",
    };
    private String[] rota2=new String[]{
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/K%C4%B1z%20Kulesi.jpg?alt=media&token=40010683-2c62-4f73-9e27-b1cb4078a1bb",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Beylerbeyi%20Saray%C4%B1.jpg?alt=media&token=5c4d3d4e-4655-4776-a3c8-0253d2e76666",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Kuzguncuk%20Sahili.jpg?alt=media&token=a89629fc-da2c-4588-87bd-b4154eac572a",
    };
    private String[] rota3=new String[]{
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Kapal%C4%B1%20%C3%87ar%C5%9F%C4%B1.Jpeg?alt=media&token=b2a0f1ba-11b4-4f25-a403-4bee42fb9097",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/M%C4%B1s%C4%B1r%20%C3%87ar%C5%9F%C4%B1s%C4%B1.jpg?alt=media&token=f26e8966-1ebe-4c44-b829-f9092801a26f",
            "https://firebasestorage.googleapis.com/v0/b/istanbulrehberi-21909.appspot.com/o/Emin%C3%B6n%C3%BC%20Sahili.jpg?alt=media&token=c362fc15-262f-4b5f-a32f-d3a4cfc7c603",
    };

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((ImageView)object);
    }
    public Object instantiateItem(ViewGroup container, int i){
        ImageView mImageView=new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if(gun=="rota1")
        Glide.with(mContext).load(rota1[i]).into(mImageView);
        else if(gun=="rota2")
            Glide.with(mContext).load(rota2[i]).into(mImageView);
        else if(gun=="rota3")
            Glide.with(mContext).load(rota3[i]).into(mImageView);
       // mImageView.setImageResource(sliderId[i]);
        ((ViewPager) container).addView(mImageView, 0);

        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((ImageView) obj);
    }

}
