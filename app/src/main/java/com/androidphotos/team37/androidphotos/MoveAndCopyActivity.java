package com.androidphotos.team37.androidphotos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import model.Album;
import model.AlbumList;

import static com.androidphotos.team37.androidphotos.DashboardActivity.albums;
import static com.androidphotos.team37.androidphotos.IndividualAlbumActivity.copy;
import static com.androidphotos.team37.androidphotos.DashboardActivity.selectedAlbum;
import static com.androidphotos.team37.androidphotos.IndividualAlbumActivity.selectedPhoto;


public class MoveAndCopyActivity extends Activity implements View.OnClickListener {

    ListView listView;
    ArrayAdapter arrayAdapter;
    Album destinationAlbum;

    ArrayList<Album> albumList = albums.getAlbums();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Allison: ", "1");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_and_copy);
        getActionBar().setTitle("Move/Copy Photo");


        Button cancelBtn = findViewById(R.id.button25);
        Button confirmBtn = findViewById(R.id.button26);
        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        Log.d("Allison: ", "2");



        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, albumList);
        listView.setAdapter(arrayAdapter);
        Log.d("Allison: ", "3");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                destinationAlbum = (Album) listView.getItemAtPosition(position);
                Log.d("Allison: ", "4");


            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.d("Allison: ", "5");

        switch (v.getId()) {
            case R.id.button25: {
                Log.d("Allison: ", "6");

                Intent intent = new Intent(this, IndividualAlbumActivity.class);
                startActivity(intent);
                Log.d("Allison: ", "7");

                break;

            }
            case R.id.button26: {
                if (copy == true) {
                    Log.d("Allison: ", "8");
                    copyPhoto();
                } else {
                    Log.d("Allison: ", "10");

                    movePhoto();
                }
                Log.d("Allison: ", "11");

                Intent intent = new Intent(this, IndividualAlbumActivity.class);
                startActivity(intent);
                Log.d("Allison: ", "12");

                break;
            }


        }
    }
    public void copyPhoto() {
        Log.d("Allison: ", "9");

        destinationAlbum.addPhoto(selectedPhoto);
        albums.saveToDisk(this);
    }

    public void movePhoto() {
        Log.d("Allison: ", "11");
        selectedAlbum.removePhoto(selectedPhoto);
        destinationAlbum.addPhoto(selectedPhoto);
        albums.saveToDisk(this);
    }

    public static void setAlbumList(AlbumList aList) {
        albums = aList;
    }
}



