package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {


    String name;


    ArrayList<Photo> photos;


    public Album (String name) {
        this.name = name;
        photos = new ArrayList<Photo>();
    }



    public void addPhoto(Photo p) {

        photos.add(p);
    }

    public void removePhoto (Photo p) {
        photos.remove(p);
    }


    public void removePhotoByBitmap (Photo p) {
        for (Photo photo: photos) {
            if (photo.getBitmap().equals(p.getBitmap())){
                photos.remove(photo);
                return;
            }
        }
    }


    public void setName(String name) {

        this.name = name;
    }

    public String getName() {

        return this.name;
    }

    public ArrayList<Photo> getPhotos() {

        return this.photos;
    }

    public String toString() {

        return this.name;
    }


}
