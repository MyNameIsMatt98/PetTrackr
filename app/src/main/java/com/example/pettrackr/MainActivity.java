package com.example.pettrackr;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private petViewModel petVM;
    public static final int ADD_PET_REQUEST = 1;
    public static final int ADD_EVENT_REQUEST = 2;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123456789;//?
    private DrawerLayout drawer;
    private ImageView header;
    public List<String> petNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        header = findViewById(R.id.header_image);



       // Glide.with(this).load(R.drawable.paw)
              //  .apply(RequestOptions.circleCropTransform())
               // .into(header);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null) {//If config changes like rotate this stops fragment from breaking
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PetFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_pets);
        }


        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            /*if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
                return;
            }
            */
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }



        /*

        FloatingActionButton buttonAddPet = findViewById(R.id.button_add_pet);
        buttonAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPet.class);
                startActivityForResult(intent,ADD_PET_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.pet_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        final petAdapter adapter = new petAdapter();
        recyclerView.setAdapter(adapter);

        petVM = ViewModelProviders.of(this).get(petViewModel.class);
        petVM.getAllPets().observe(this, new Observer<List<Pet>>() {
            @Override
            public void onChanged(@Nullable List<Pet> pets) {
                adapter.setPets(pets);

            }
        });
        */

       /* String testImg = (ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getResources().getResourcePackageName(R.drawable.paw2)
                + '/' + getResources().getResourceTypeName(R.drawable.paw2) + '/' + getResources().getResourceEntryName(R.drawable.paw2) );
        Pet testPet = new Pet();
        testPet.setName("Fizz");
        testPet.setType("Cat");
        testPet.setImage(testImg);
        petVM.insert(testPet);
        */


    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== ADD_PET_REQUEST && resultCode == RESULT_OK){
            String name = data.getStringExtra(AddPet.EXTRA_NAME);
            int age = data.getIntExtra(AddPet.EXTRA_AGE,0);
            String type = data.getStringExtra(AddPet.EXTRA_TYPE);
            String notes = data.getStringExtra(AddPet.EXTRA_NOTES);
            String img = data.getStringExtra(AddPet.EXTRA_IMAGE);

            Pet pet = new Pet(name,age,type,img,notes);
            petVM.insert(pet);

            Toast.makeText(this, "Pet Added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Pet Not Saved", Toast.LENGTH_SHORT).show();
        }
    }
    */
   public int getImage(String imageName) {

       int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

       return drawableResourceId;
   }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_pets:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PetFragment()).commit();
                break;
            case R.id.nav_Schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ScheduleFragment()).commit();
                break;
            case R.id.nav_Reminders:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RemindersFragment()).commit();
                break;
            case R.id.nav_Today:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TodayFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
