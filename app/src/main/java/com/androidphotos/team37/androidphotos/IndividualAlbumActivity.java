package com.androidphotos.team37.androidphotos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Album;
import model.AlbumList;
import model.Photo;

import static com.androidphotos.team37.androidphotos.DashboardActivity.albums;
import static com.androidphotos.team37.androidphotos.DashboardActivity.selectedAlbum;


public class IndividualAlbumActivity extends Activity implements View.OnClickListener {
    public static final int PICK_IMAGE = 1;
    public ListView listView;
    public ImageView imageView;
    public ArrayAdapter arrayAdapter;
    public static Photo selectedPhoto;
    private String currentImagePath;
    public static boolean copy;
    ArrayList<Photo> photoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_individual_album);
        getActionBar().setTitle(selectedAlbum.getName());


        Button viewEditBtn = findViewById(R.id.button2);
        Button deleteBtn = findViewById(R.id.button4);
        Button addBtn = findViewById(R.id.button10);
        Button backBtn = findViewById(R.id.button12);
        Button slideshowBtn = findViewById(R.id.button11);
        Button moveBtn = findViewById(R.id.button9);
        Button copyBtn = findViewById(R.id.button3);
        moveBtn.setOnClickListener(this);
        copyBtn.setOnClickListener(this);
        slideshowBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        viewEditBtn.setOnClickListener(this);


        Log.d("Allison: ", selectedAlbum.toString());


        imageView = findViewById(R.id.imageView4);


        photoList = selectedAlbum.getPhotos();
        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<Photo>(this, android.R.layout.simple_list_item_1, photoList);
        listView.setAdapter(arrayAdapter);
        if (photoList.size() > 0) {
            selectedPhoto = (Photo) listView.getItemAtPosition(0);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPhoto = (Photo) listView.getItemAtPosition(position);
                imageView.setImageBitmap(selectedPhoto.getBitmap());


            }
        });
        if (selectedAlbum.getPhotos().size() > 0) {
            imageView.setImageBitmap(selectedAlbum.getPhotos().get(0).getBitmap());
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2: {
                if (selectedPhoto == null) {
                    Log.d("Allison: ", "hi");

                    return;
                }
                if (photoList.size() < 1) {
                    return;
                }
                Log.d("Allison: ", "going to edit photo");
                Intent intent = new Intent(this, IndividualPhotoActivity.class);
                IndividualPhotoActivity.setAlbumList(albums);
                startActivity(intent);
                break;
            }
            case R.id.button9: {
                if (selectedPhoto == null) {
                    Log.d("Allison: ", "hi");

                    return;
                }
                if (photoList.size() < 1) {
                    return;
                }
                Log.d("Allison: ", "going back to move screen");
                Intent intent = new Intent(this, MoveAndCopyActivity.class);
                copy = false;
                MoveAndCopyActivity.setAlbumList(albums);
                startActivity(intent);
                break;
            }
            case R.id.button4: {
                if (photoList.size() < 1) {
                    return;
                }
                Log.d("Allison: ", "case 4");
                selectedAlbum.removePhoto(selectedPhoto);
                albums.saveToDisk(this);
                if (selectedAlbum.getPhotos().size() > 0) {
                    selectedPhoto = (Photo) listView.getItemAtPosition(0);
                    imageView.setImageBitmap(selectedAlbum.getPhotos().get(0).getBitmap());
                }
                else{
                    imageView.setImageDrawable(null);
                }
                arrayAdapter.notifyDataSetChanged();
                break;
            }
            case R.id.button3: {
                if (selectedPhoto == null) {
                    return;
                }
                if (photoList.size() < 1) {
                    return;
                }
                Log.d("Allison: ", "hi");
                Intent intent = new Intent(this, MoveAndCopyActivity.class);
                MoveAndCopyActivity.setAlbumList(albums);
                copy = true;
                startActivity(intent);
                break;
            }
            case R.id.button10: {
                addPhoto();
                break;
            }
            case R.id.button11: {
                if (selectedPhoto == null) {
                    Log.d("Allison: ", "hi");

                    return;
                }
                if (photoList.size() < 1) {
                    return;
                }
                Intent intent = new Intent(this, SlideshowActivity.class);
                SlideshowActivity.setAlbumList(albums);
                startActivity(intent);
                break;
            }
            case R.id.button12: {
                Intent intent = new Intent(this, DashboardActivity.class);

                startActivity(intent);
                break;
            }
        }
    }

    public void addPhoto() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            Log.d("Allison: ", "when uploaded, uri is" + imageUri.toString());

            BitmapDrawable bitmap = (BitmapDrawable) imageView.getDrawable();
            Bitmap bit = bitmap.getBitmap();
            imageView.setImageBitmap(bit);

            File file = new File(imageUri.getPath());

            selectedAlbum.addPhoto(new Photo(file.getName(), bit));
            arrayAdapter.notifyDataSetChanged();

            albums.saveToDisk(this);


        }
    }


    public static void setAlbumList(AlbumList aList) {

        albums = aList;
    }

    public static void setAlbum(Album a) {

        selectedAlbum = a;
    }

}


