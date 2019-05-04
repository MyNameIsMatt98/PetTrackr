package com.example.pettrackr;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.pettrackr.MainActivity.ADD_PET_REQUEST;

public class PetFragment extends Fragment {
    private petViewModel petVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Pets");
        View v = inflater.inflate(R.layout.fragment_pets,container,false);
        FloatingActionButton buttonAddPet = v.findViewById(R.id.button_add_pet);
        buttonAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddPet.class);
                startActivityForResult(intent,ADD_PET_REQUEST);
            }
        });

        RecyclerView recyclerView = v.findViewById(R.id.pet_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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







        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== ADD_PET_REQUEST && resultCode == RESULT_OK){
            String name = data.getStringExtra(AddPet.EXTRA_NAME);
            int age = data.getIntExtra(AddPet.EXTRA_AGE,0);
            String type = data.getStringExtra(AddPet.EXTRA_TYPE);
            String notes = data.getStringExtra(AddPet.EXTRA_NOTES);
            String img = data.getStringExtra(AddPet.EXTRA_IMAGE);

            Pet pet = new Pet(name,age,type,img,notes);
            petVM.insert(pet);

            Toast.makeText(getActivity(), "Pet Added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Pet Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

}
