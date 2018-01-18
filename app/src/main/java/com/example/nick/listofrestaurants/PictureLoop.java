package com.example.nick.listofrestaurants;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

/**
 * Created by nick_ on 1/17/2018.
 */


public class PictureLoop implements Runnable {

    private Context context;
    private String[] images;

    public PictureLoop(Context context, String[] images){
        this.context = context;
        this.images = images;
    }

    @Override
    public void run() {

    }
}
