package model;

import java.io.Serializable;

public class Tag implements Comparable<Tag>, Serializable{

    String key;

    String value;

    public Tag(String key, String value) {
        this.key = key;
        this.value=value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey (String key) {

        this.key = key;
    }

    public String getValue() {

        return this.value;
    }

    public void setValue(String value) {

        this.value=value;
    }

    public String toString() {
        return  this.getKey() + "=" + this.getValue();
    }

    @Override
    public int compareTo(Tag t) {
        if (getKey().toLowerCase().equals(t.getKey().toLowerCase())){
            return getValue().toLowerCase().compareTo(t.getValue().toLowerCase());
        } else {
            return getKey().toLowerCase().compareTo(t.getKey().toLowerCase());
        }
    }

}