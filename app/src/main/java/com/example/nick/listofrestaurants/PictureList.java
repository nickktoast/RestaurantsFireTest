package com.example.nick.listofrestaurants;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Picture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PictureList extends AppCompatActivity {

    private StorageReference mStorageRef;

    public static String[] memes = {
            "deway1.jpg", "deway2.jpg", "deway3.jpg", "deway4.jpg", "deway5.jpg", "deway6.jpg", "deway7.jpg",
            "deway8.jpg"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_list);
        ListView listView = (ListView) findViewById(R.id.pictureList);

        listView.setAdapter(
                new ImageListAdapter(
                        PictureList.this,
                        memes
                )
        );

        //mStorageRef = FirebaseStorage.getInstance().getReference(MainActivity.FB_STORAGE_PATH);

    }

}
