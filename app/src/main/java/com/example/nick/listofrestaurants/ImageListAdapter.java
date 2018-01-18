package com.example.nick.listofrestaurants;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by nick_ on 1/13/2018.
 */

public class ImageListAdapter extends ArrayAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String[] images;

    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private StorageReference pathReference = mStorageRef.child("image");


    public ImageListAdapter(Context context, String[] images){
        super(context, R.layout.custom_list_view, images);
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_list_view, parent, false);
        }

        //Glide.with(context).load(images[position]).into((ImageView) convertView);
        /*Glide.with(context)
                .load(pathReference + images[position])
                .into((ImageView) convertView);*/
        System.out.println(pathReference.child(images[position]));

        GlideApp.with(context)
                .load(pathReference.child(images[position]))
                .into((ImageView) convertView);

        return convertView;
    }

}