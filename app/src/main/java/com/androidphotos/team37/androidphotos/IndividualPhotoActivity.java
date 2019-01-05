package com.androidphotos.team37.androidphotos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import model.AlbumList;
import model.Photo;
import model.Tag;

import static com.androidphotos.team37.androidphotos.DashboardActivity.albums;
import static com.androidphotos.team37.androidphotos.DashboardActivity.selectedAlbum;
import static com.androidphotos.team37.androidphotos.IndividualAlbumActivity.selectedPhoto;


public class IndividualPhotoActivity extends Activity implements View.OnClickListener {
    ImageView imageView;
    ListView listView;
    ArrayAdapter arrayAdapter;
    Tag selectedTag;
    ArrayList<Tag> tagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_photo);
        getActionBar().setTitle(selectedPhoto.toString());



        Button addTagBtn = findViewById(R.id.button13);
        Button deleteTagBtn = findViewById(R.id.button15);
        Button editTagBtn = findViewById(R.id.button14);
        Button backBtn = findViewById(R.id.button16);
        backBtn.setOnClickListener(this);
        addTagBtn.setOnClickListener(this);
        deleteTagBtn.setOnClickListener(this);
        editTagBtn.setOnClickListener(this);


        imageView = findViewById(R.id.imageView2);


        imageView.setImageBitmap(selectedPhoto.getBitmap());


        tagList = selectedPhoto.getTags();

        listView = (ListView) findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, tagList);


        listView.setAdapter(arrayAdapter);

        if (tagList.size() > 0) {
            selectedTag = (Tag) listView.getItemAtPosition(0);

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTag = (Tag) listView.getItemAtPosition(position);



            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button13: {
                Log.d("Allison: ", "this is button 13");
                newTag();
                break;
            }
            case R.id.button15: {
                deleteTag();
                break;
            }
            case R.id.button14: {
                editTag();
                break;
            }
            case R.id.button16: {
                SearchEngineActivity.setAlbumList(albums);
                Log.d("Allison: ", "going back to albums");
                Intent intent = new Intent(this, IndividualAlbumActivity.class);
                IndividualAlbumActivity.setAlbumList(albums);
                startActivity(intent);
                break;
            }
        }
    }

    public void newTag() {
       String newType = ((EditText) findViewById(R.id.editText4)).getText().toString();
        String newValue = ((EditText) findViewById(R.id.editText5)).getText().toString();
        if (newType.length()==0 || newValue.length()==0) {
            return;
        }

        if (!(newType.toLowerCase().equals("person") || newType.toLowerCase().equals("location"))){
            return;
        }

        Tag newTag = new Tag (newType, newValue);
        selectedPhoto.addTag(newTag);
        arrayAdapter.notifyDataSetChanged();
        albums.saveToDisk(this);


    }

    public void editTag() {
        if (selectedTag==null) {
            return;
        }
        if (tagList.size() < 1) {
            return;
        }
        String newType = ((EditText) findViewById(R.id.editText4)).getText().toString();
        String newValue = ((EditText) findViewById(R.id.editText5)).getText().toString();
        if (newType.length()==0 || newValue.length()==0) {
            return;
        }
        if (!(newType.toLowerCase().equals("person") || newType.toLowerCase().equals("location"))){
            return;
        }
        selectedTag.setKey(newType);
        selectedTag.setValue(newValue);
        arrayAdapter.notifyDataSetChanged();
        albums.saveToDisk(this);


    }
    public void deleteTag() {
        if (selectedTag==null) {
            return;
        }
        if (tagList.size() < 1) {
            return;
        }
        selectedPhoto.removeTag(selectedTag);
        albums.saveToDisk(this);
        arrayAdapter.notifyDataSetChanged();

    }

    public static void setAlbumList(AlbumList aList) {

        albums = aList;
    }
}