package com.androidphotos.team37.androidphotos;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

import model.Album;
import model.AlbumList;
import model.Photo;


public class DashboardActivity extends Activity implements View.OnClickListener {
    ListView listView;
    ImageView imageView;
    public static Album selectedAlbum;
    public static AlbumList albums;
    ArrayAdapter<Album> arrayAdapter;
    ArrayList<Album> albumList;


//    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        albums = albums.loadFromDisk(this);
        albumList = albums.getAlbums();


        Button renameAlbumBtn = findViewById(R.id.button5);
        Button searchBtn = findViewById(R.id.button6);
        Button viewAlbumBtn = findViewById(R.id.button7);
        Button newAlbumBtn = findViewById(R.id.button);
        Button deleteAlbumBtn = findViewById(R.id.button8);
        deleteAlbumBtn.setOnClickListener(this);
        newAlbumBtn.setOnClickListener(this);
        viewAlbumBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        renameAlbumBtn.setOnClickListener(this);


        imageView = findViewById(R.id.imageView);

        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, albumList);
        listView.setAdapter(arrayAdapter);
        if (albumList.size() > 0) {
            selectedAlbum = (Album) listView.getItemAtPosition(0);
            if (selectedAlbum.getPhotos().size() > 0) {
                imageView.setImageBitmap(selectedAlbum.getPhotos().get(0).getBitmap());
            }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedAlbum = (Album) listView.getItemAtPosition(position);

                if (selectedAlbum.getPhotos().size() > 0) {
                    Photo firstPhoto = selectedAlbum.getPhotos().get(0);

                    Log.d("Allison: ", "3");

                    imageView.setImageBitmap(firstPhoto.getBitmap());
                }
                else {
                    imageView.setImageDrawable(null);

                }

            }
        });


        Log.d("Allison: ", "got through on create");


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button5: {
                String newName = ((EditText) findViewById(R.id.editText)).getText().toString();
                if (selectedAlbum == null || newName.length() == 0) {
                    return;
                }
                if (albumList.size() < 1) {
                    return;
                }
                Album newAlbum = selectedAlbum;
                newAlbum.setName(newName);
                albums.deleteAlbumByName(selectedAlbum.getName());
                albums.addAlbum(newAlbum);
                albums.saveToDisk(this);

                arrayAdapter.notifyDataSetChanged();

                Log.d("Allison: ", "this is button 5");
                break;
            }
            case R.id.button8: {
                if (selectedAlbum == null) {
                    return;
                }
                if (albumList.size() < 1) {
                    return;
                }
                albums.deleteAlbumByName(selectedAlbum.getName());
                albums.saveToDisk(this);

                arrayAdapter.notifyDataSetChanged();

                if (albumList.size() > 0) {
                    selectedAlbum = (Album) listView.getItemAtPosition(0);
                    if (selectedAlbum.getPhotos().size() > 0) {
                        imageView.setImageBitmap(selectedAlbum.getPhotos().get(0).getBitmap());
                    }
                    else {
                        imageView.setImageDrawable(null);

                    }
                }
                else {
                    imageView.setImageDrawable(null);

                }

                Log.d("Allison: ", "this is button 8");
                break;
            }
            case R.id.button: {
                String newName = ((EditText) findViewById(R.id.editText2)).getText().toString();
                if (newName.length() == 0) {
                    return;
                }
                albums.addAlbum(new Album(newName));
                albums.saveToDisk(this);

                arrayAdapter.notifyDataSetChanged();

                Log.d("Allison: ", "button clicked");
                break;
            }
            case R.id.button6: {
                SearchEngineActivity.setAlbumList(albums);
                Log.d("Allison: ", "going to search");
                Intent intent = new Intent(this, SearchEngineActivity.class);
                SearchEngineActivity.setAlbumList(albums);
                startActivity(intent);
                break;
            }
            case R.id.button7: {
                if (albumList.size() < 1) {
                    return;
                }
                Log.d("Allison: ", "going to view album");

                Intent intent = new Intent(this, IndividualAlbumActivity.class);
                startActivity(intent);
                break;
            }
        }

    }


}