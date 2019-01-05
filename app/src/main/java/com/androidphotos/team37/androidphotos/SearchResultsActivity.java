package com.androidphotos.team37.androidphotos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Album;
import model.AlbumList;
import model.Photo;

import static com.androidphotos.team37.androidphotos.DashboardActivity.albums;
import static com.androidphotos.team37.androidphotos.SearchEngineActivity.resultsAlbum;
import static com.androidphotos.team37.androidphotos.IndividualAlbumActivity.selectedPhoto;
import static com.androidphotos.team37.androidphotos.DashboardActivity.selectedAlbum;




public class SearchResultsActivity extends Activity implements View.OnClickListener {
    public ListView listView;
    public ImageView imageView;
    public ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        getActionBar().setTitle("Search Results");


        Button viewPhotoBtn = findViewById(R.id.button21);
        Button slideShowBtn = findViewById(R.id.button22);
        Button editSearchBtn = findViewById(R.id.button23);
        Button doneBtn = findViewById(R.id.button24);
        editSearchBtn.setOnClickListener(this);
        doneBtn.setOnClickListener(this);
        slideShowBtn.setOnClickListener(this);
        viewPhotoBtn.setOnClickListener(this);

        imageView = findViewById(R.id.imageView4);

        selectedAlbum = resultsAlbum;



        ArrayList<Photo> photoList = resultsAlbum.getPhotos();
        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<Photo>(this, android.R.layout.simple_list_item_1, photoList);
        listView.setAdapter(arrayAdapter);
        if (photoList.size() > 0) {
            selectedPhoto = (Photo) listView.getItemAtPosition(0);
            imageView.setImageBitmap(selectedPhoto.getBitmap());

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPhoto = (Photo) listView.getItemAtPosition(position);
                imageView.setImageBitmap(selectedPhoto.getBitmap());



            }
        });


    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button21: {
                if (resultsAlbum.getPhotos().size() < 1) {
                    return;
                }
                Log.d("Allison: ", "going to edit photo");
                Intent intent = new Intent(this, IndividualPhotoActivity.class);
                IndividualPhotoActivity.setAlbumList(albums);
                startActivity(intent);
                break;
            }
            case R.id.button22: {
                if (resultsAlbum.getPhotos().size() < 1) {
                    return;
                }
                SearchEngineActivity.setAlbumList(albums);
                Log.d("Allison: ", "going to slidshow");
                Intent intent = new Intent(this, SearchEngineActivity.class);
                SearchEngineActivity.setAlbumList(albums);
                startActivity(intent);
                break;
            }
            case R.id.button23: {
                Intent intent = new Intent(this, SearchEngineActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button24: {
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
                break;
            }
        }

    }

    public static void setAlbumList(AlbumList aList) {

        albums = aList;
    }


}
