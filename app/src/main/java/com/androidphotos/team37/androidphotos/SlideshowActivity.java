package com.androidphotos.team37.androidphotos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import model.Album;
import model.AlbumList;

import static com.androidphotos.team37.androidphotos.DashboardActivity.albums;
import static com.androidphotos.team37.androidphotos.DashboardActivity.selectedAlbum;


public class SlideshowActivity extends Activity implements View.OnClickListener {
    int photoIndex = 0;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        getActionBar().setTitle(selectedAlbum.getName());


        Button nextBtn = findViewById(R.id.button27);
        Button prevBtn = findViewById(R.id.button28);
        nextBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);


        imageView = findViewById(R.id.imageView3);

        imageView.setImageBitmap(selectedAlbum.getPhotos().get(photoIndex).getBitmap());


    }
    @Override
    protected void onResume() {
            super.onResume();
            setContentView(R.layout.activity_slideshow);
            getActionBar().setTitle(selectedAlbum.getName());


            Button nextBtn = findViewById(R.id.button27);
            Button prevBtn = findViewById(R.id.button28);
            nextBtn.setOnClickListener(this);
            prevBtn.setOnClickListener(this);


            imageView = findViewById(R.id.imageView3);

            imageView.setImageBitmap(selectedAlbum.getPhotos().get(photoIndex).getBitmap());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button27: {
                photoIndex++;
                if (photoIndex > selectedAlbum.getPhotos().size()-1) {
                    photoIndex=0;
                }
                imageView.setImageBitmap(selectedAlbum.getPhotos().get(photoIndex).getBitmap());
                break;
            }
            case R.id.button28: {
                photoIndex--;
                if (photoIndex == -1) {
                    photoIndex=selectedAlbum.getPhotos().size()-1;
                }
                imageView.setImageBitmap(selectedAlbum.getPhotos().get(photoIndex).getBitmap());
                break;

            }
        }
    }


    public static void setAlbumList(AlbumList aList) {

        albums = aList;
    }
}