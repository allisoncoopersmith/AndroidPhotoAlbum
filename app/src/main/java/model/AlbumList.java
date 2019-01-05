package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


public class AlbumList implements Serializable{


    private static final long serialVersionUID = 100L;

    public ArrayList<Album> albums;

    public static final String fileName = "pad.ser";



    public AlbumList() {

        albums = new ArrayList<Album>();
    }

    public void addAlbum( Album album) {
        albums.add(album);
    }


    public ArrayList<Album> getAlbums() {

        return albums;
    }


    public void deleteAlbum(Album album) {

        albums.remove(album);
    }

    public boolean deleteAlbumByName (String string) {
        for (Album album: albums) {
            if (album.getName().equals(string)) {
                albums.remove(album);
                return true;
            }
        }
        return false;
    }


    public Album isAlbumInList(String albumName) {
        for (Album a : albums) {
            if (albumName.equals(a.getName())) {
                return a;
            }
        }

        return null;
    }


    public String toString() {
        String albumList = "";

        if (albums == null) {
            return albumList;
        }

        for (Album a : albums) {
            albumList += a.toString() + "\n";
        }
        return albumList;
    }



    public static AlbumList loadFromDisk(Context context){
        AlbumList albums = new AlbumList();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            albums = (AlbumList) ois.readObject();

            if (albums.getAlbums() == null) {
                return albums;
            }
            fis.close();
            ois.close();
        } catch (FileNotFoundException e) {
            Log.d("Allison: ", "file not found");

            return albums;
        } catch (IOException e) {
            Log.d("Allison: ", "IOException");

            return null;
        } catch (ClassNotFoundException e) {
            Log.d("Allison: ", "class not found");

            return null;
        } catch (Exception e) {
            Log.d("Allison: ", "other exception");


            return null;
        }
        return albums;
    }

    public void saveToDisk(Context context){
        File file = new File(context.getFilesDir(), fileName);

        ObjectOutputStream oos;
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}