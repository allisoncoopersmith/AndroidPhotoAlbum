package com.androidphotos.team37.androidphotos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import model.Album;
import model.AlbumList;
import model.Photo;
import model.Tag;

import static com.androidphotos.team37.androidphotos.DashboardActivity.albums;


public class SearchEngineActivity extends Activity implements View.OnClickListener {

    public static Album resultsAlbum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_engine);
        getActionBar().setTitle("Search Engine");


        Button cancelBtn = findViewById(R.id.button17);
        Button singleSearchBtn = findViewById(R.id.button18);
        Button andSearchBtn = findViewById(R.id.button19);
        Button orSearchBtn = findViewById(R.id.button20);
        orSearchBtn.setOnClickListener(this);
        andSearchBtn.setOnClickListener(this);
        singleSearchBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button17: {
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button18: {
                resultsAlbum = singleBtnClicked();
                Intent intent = new Intent(this, SearchResultsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button19: {
                resultsAlbum = andBtnClicked();
                Log.d("Allison: ", "going to view album");
                Intent intent = new Intent(this, SearchResultsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.button20: {
                resultsAlbum = orBtnClicked();
                Log.d("Allison: ", "going to view album");
                Intent intent = new Intent(this, SearchResultsActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    public Album singleBtnClicked() {
        ArrayList<Photo> allPhotos = getAllPhotos();
        ArrayList<Photo> result = new ArrayList<Photo>();
        if (allPhotos.size() == 0) {
            return new Album("result");

        }
        String key = ((EditText) findViewById(R.id.editText3)).getText().toString();

        String value = ((EditText) findViewById(R.id.editText6)).getText().toString();

        if (key.length() == 0 || value.length() == 0) {
            return new Album("result");

        }
        for (Photo p : allPhotos) {
            ArrayList<Tag> tags = p.getTags();
            for (Tag t : tags) {
                String thisKey = t.getKey();
                String thisValue = "";
                if (value.length() < t.getValue().length()) {
                    thisValue += t.getValue().substring(0, value.length());
                } else {
                    thisValue = t.getValue();
                }
                Log.d("Allison: ", thisValue + value);

                if (thisKey.toLowerCase().equals(key.toLowerCase()) && thisValue.toLowerCase().equals(value.toLowerCase())) {
                    if (checkForDuplicates(p, result)) {
                        result.add(p);
                    }
                }
            }
        }

        Album resultingAlbum = new Album("Results Album");
        resultingAlbum.getPhotos().addAll(result);
        return resultingAlbum;
    }


    public Album andBtnClicked() {

        ArrayList<Photo> allPhotos = getAllPhotos();
        ArrayList<Photo> intermediate = new ArrayList<Photo>();
        ArrayList<Photo> result = new ArrayList<Photo>();

        if (allPhotos.size() == 0) {
            return new Album("result");
        }
        String key1 = ((EditText) findViewById(R.id.editText3)).getText().toString();
        String key2 = ((EditText) findViewById(R.id.editText7)).getText().toString();
        String value1 = ((EditText) findViewById(R.id.editText6)).getText().toString();
        String value2 = ((EditText) findViewById(R.id.editText8)).getText().toString();

        Log.d("Allison: ", key1 + value1);
        Log.d("Allison: ", key2 + value2);


        if (key1.length() == 0 || value1.length() == 0 || key2.length() == 0 || value2.length() == 0) {

            return new Album("result");
        }
        for (Photo p : allPhotos) {
            ArrayList<Tag> tags = p.getTags();
            for (Tag t : tags) {
                String thisKey = t.getKey();
                String thisValue = "";
                if (value1.length() < t.getValue().length()) {
                    thisValue += t.getValue().substring(0, value1.length());
                } else {
                    thisValue = t.getValue();
                }

                if (thisKey.toLowerCase().equals(key1.toLowerCase()) && thisValue.toLowerCase().equals(value1.toLowerCase())) {
                    if (checkForDuplicates(p, intermediate)) {
                        intermediate.add(p);
                    }
                }
            }
        }
        for (Photo p : intermediate) {
            ArrayList<Tag> tags = p.getTags();
            for (Tag t : tags) {
                String thisKey = t.getKey();
                String thisValue = "";
                if (value2.length() < t.getValue().length()) {
                    thisValue += t.getValue().substring(0, value2.length());
                } else {
                    thisValue = t.getValue();
                }
                if (thisKey.toLowerCase().equals(key2.toLowerCase()) && thisValue.toLowerCase().equals(value2.toLowerCase())) {
                    if (checkForDuplicates(p, result)) {
                        result.add(p);
                    }
                }
            }
        }
        Album resultingAlbum = new Album("Results Album");
        resultingAlbum.getPhotos().addAll(result);
        return resultingAlbum;

    }

    public Album orBtnClicked() {
        ArrayList<Photo> allPhotos = getAllPhotos();
        ArrayList<Photo> result = new ArrayList<Photo>();

        if (allPhotos.size() == 0) {
            return new Album("result");
        }

        String key1 = ((EditText) findViewById(R.id.editText3)).getText().toString();
        String key2 = ((EditText) findViewById(R.id.editText7)).getText().toString();
        String value1 = ((EditText) findViewById(R.id.editText6)).getText().toString();
        String value2 = ((EditText) findViewById(R.id.editText8)).getText().toString();


        if (key1.length() == 0 || value1.length() == 0 || key2.length() == 0 || value2.length() == 0) {
            return new Album("result");
        }
        for (Photo p : allPhotos) {
            ArrayList<Tag> tags = p.getTags();
            for (Tag t : tags) {
                String thisKey = t.getKey();
                String thisValue = "";
                if (value1.length() < t.getValue().length()) {
                    thisValue += t.getValue().substring(0, value1.length());
                } else {
                    thisValue = t.getValue();
                }

                if ((thisKey.toLowerCase().equals(key1.toLowerCase()) && thisValue.toLowerCase().equals(value1.toLowerCase()))) {
                    if (checkForDuplicates(p, result)) {
                        result.add(p);
                    }
                }
            }
        }
        for (Photo ph : allPhotos) {
            ArrayList<Tag> tag = ph.getTags();
            for (Tag t : tag) {
                String thisKey = t.getKey();
                String thisValue = "";
                if (value2.length() < t.getValue().length()) {
                    thisValue += t.getValue().substring(0, value2.length());
                } else {
                    thisValue = t.getValue();
                }

                if (((thisKey.toLowerCase().equals(key2.toLowerCase()) && thisValue.toLowerCase().equals(value2.toLowerCase())))) {
                    if (checkForDuplicates(ph, result)) {
                        result.add(ph);
                    }
                }
            }
        }
        Album resultingAlbum = new Album("Results Album");
        resultingAlbum.getPhotos().addAll(result);
        return resultingAlbum;
    }


    public ArrayList<Photo> getAllPhotos() {
        ArrayList<Photo> allPhotos = new ArrayList<Photo>();
        ArrayList<Album> allAlbums = albums.getAlbums();
        for (Album a : allAlbums) {
            ArrayList<Photo> photos = a.getPhotos();
            for (Photo p : photos) {
                allPhotos.add(p);

            }
        }
        Log.d("Allison: ", "all the photos are of size" + String.valueOf(allPhotos.size()));


        return allPhotos;
    }

    public boolean checkForDuplicates(Photo p, ArrayList<Photo> photos) {
        for (Photo pho : photos) {
            if (pho.equals(p)) {
                return false;
            }
        }
        return true;

    }


    public static void setAlbumList(AlbumList aList) {
        albums = aList;
    }
}
