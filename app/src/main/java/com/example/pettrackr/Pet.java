package com.example.pettrackr;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

@Entity(tableName = "pet_table")
public class Pet {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Pet(String name, int age, String type, String image, String notes) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.image = image;
        this.notes = notes;
    }

    private String name;

    private int age;

    private String type;

    private String image; //store uri for image and convert to string https://www.reddit.com/r/androiddev/comments/98uqit/saving_image_in_room_database/
    //https://stackoverflow.com/questions/50609378/saving-image-path-from-gallery-to-room-database-and-display-it-in-recycler-list

    private String notes;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public String getNotes() {
        return notes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setId(int i ){this.id = i;}

    public Uri getUri(){
        Uri uri = Uri.parse(this.image);
        return uri;
    }
}

