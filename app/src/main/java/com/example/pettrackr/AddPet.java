package com.example.pettrackr;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddPet extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 0;
    static final int REQUEST_GALLERY = 1;
    public static final String EXTRA_NAME = "com.example.pettrackr.EXTRA_NAME";
    public static final String EXTRA_AGE = "com.example.pettrackr.EXTRA_AGE";
    public static final String EXTRA_TYPE = "com.example.pettrackr.EXTRA_TYPE";
    public static final String EXTRA_NOTES = "com.example.pettrackr.EXTRA_NOTES";
    public static final String EXTRA_IMAGE = "com.example.pettrackr.EXTRA_IMAGE";
    Uri outputFileUri;


    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1234567891;

    private ImageView addImage;
    private EditText editTextPetName;
    private EditText editTextPetType;
    private EditText editTextPetAge;
    private EditText editTextPetNotes;
    private Button addPet;
    private TextView imagePath;

    private ImageButton takePhoto;
    private ImageButton fromGallery;

    final String dir =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ "/Folder/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_pet);





        File newdir = new File(dir);
        newdir.mkdirs();

        addImage = findViewById(R.id.selectImage);
        editTextPetAge = findViewById(R.id.selectPetAge);
        editTextPetName = findViewById(R.id.selectPetName);
        editTextPetType = findViewById(R.id.selectPetType);
        editTextPetNotes = findViewById(R.id.selectPetNotes);
        imagePath = findViewById(R.id.imagePath);
        addPet = findViewById(R.id.save_button);
        takePhoto = findViewById(R.id.CameraButton);
        fromGallery = findViewById(R.id.GalleryButton);
        Toolbar toolbar = findViewById(R.id.toolbarAddPet);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Pet");
        String testImg = (ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(R.drawable.paw2)
                + '/' + getResources().getResourceTypeName(R.drawable.paw2) + '/' + getResources().getResourceEntryName(R.drawable.paw2) );
        imagePath.setText(testImg);//default IMAGE
        addButton();
        tapImg();
        cameraButton();
        galleryButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_pet_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_pet:
                savePet();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }
    private void savePet(){
        String image = imagePath.getText().toString();
        String name = editTextPetName.getText().toString();
        int age =0;
        String type ="";
        String notes ="";
        if (!editTextPetAge.getText().toString().isEmpty()){
            age = Integer.valueOf(editTextPetAge.getText().toString());
        }
        if (!editTextPetNotes.getText().toString().isEmpty()){
            notes = editTextPetNotes.getText().toString();
        }
        if(!editTextPetType.getText().toString().isEmpty()){
            type = editTextPetType.getText().toString();
        }
        if(name.trim().isEmpty()){
            Toast.makeText(this, "Enter a Name", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME,name);
        data.putExtra(EXTRA_AGE,age);
        data.putExtra(EXTRA_TYPE,type);
        data.putExtra(EXTRA_NOTES,notes);
        data.putExtra(EXTRA_IMAGE,image);

        setResult(RESULT_OK,data);
        finish();

    }
    public void addButton(){
        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePet();
            }
        });
    }
    private void dispatchTakePictureIntent() {
        //https://stackoverflow.com/questions/48117511/exposed-beyond-app-through-clipdata-item-geturi
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());



        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
                return;
            }

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }
        //https://stackoverflow.com/questions/17426178/tell-the-camera-intent-to-save-the-taken-image
        String file = dir+ DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString()+".jpg";
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        } catch (IOException e) {}

        outputFileUri = Uri.fromFile(newfile);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,outputFileUri);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void dispatchChooseGallery(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (pickPhoto.resolveActivity(getPackageManager())!=null){
            startActivityForResult(pickPhoto , REQUEST_GALLERY);
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
           // Bitmap imageBitmap = (Bitmap) extras.get("data");
           // addImage.setImageBitmap(imageBitmap);
            //Bundle extra = data.getBundleExtra("outputFileUri");
            Uri temp = data.getData();
            imagePath.setText(temp.toString());
        }
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            RequestOptions crop = new RequestOptions();
            crop.centerCrop();
            Glide.with(this)
                    .load(selectedImage)
                    .apply(crop)
                    .into(addImage);
            //addImage.setImageURI(selectedImage);
            imagePath.setText(selectedImage.toString());
        }
    }

    public void tapImg(){
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dispatchTakePictureIntent();

            }
        });
    }

    public void cameraButton(){
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dispatchTakePictureIntent();
            }
        });
    }
    public void galleryButton(){
        fromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchChooseGallery();
            }
        });
    }

//https://developer.android.com/training/camera/photobasics need to save image now
}
