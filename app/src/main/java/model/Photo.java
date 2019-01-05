

package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class Photo implements Serializable {


    ArrayList<Tag> tags;
    final int width;
    final int height;
    final int [] pixels;
    String uri;


    public Photo(String i, Bitmap b) {
        width = b.getWidth();
        height = b.getHeight();
        pixels = new int [width*height];
        b.getPixels(pixels,0,width,0,0,width,height);
        tags = new ArrayList<Tag>();
        uri = i;
    }


    public void addTag(Tag t) {

        tags.add(t);
    }

    public void removeTag(Tag t) {

        tags.remove(t);
    }

    public ArrayList<Tag> getTags() {
        return this.tags;
    }

    public String getUri() {

        return this.uri;
    }
    public String toString() {

        return this.uri;
    }

    public Bitmap getBitmap(){
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
    }




}