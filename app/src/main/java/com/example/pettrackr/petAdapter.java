package com.example.pettrackr;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class petAdapter extends RecyclerView.Adapter<petAdapter.petHolder> {
    private List<Pet> pets = new ArrayList<>();
    private List<String> petNames = new ArrayList<>();

    @NonNull
    @Override
    public petHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pet_item,viewGroup,false);
        return new petHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull petHolder petHolder, int i) {
        Pet currentPet = pets.get(i);
        petHolder.namePet.setText(currentPet.getName());
        //petHolder.imagePet.setImageURI(currentPet.getUri());
        Context c = petHolder.imagePet.getContext();
        Uri pic = Uri.parse(currentPet.getImage());
        String path = currentPet.getImage().substring(10);
        RequestOptions crop = new RequestOptions();
        crop.centerCrop();
        Glide.with(c)
                .load(currentPet.getUri())
                .apply(crop)
                .into(petHolder.imagePet);
       petHolder.typePet.setText(currentPet.getType());
        //petHolder.typePet.setText(path);
        petNames.add(currentPet.getName());

    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public void setPets(List<Pet> pets){
        this.pets = pets;
        notifyDataSetChanged();
    }

    public List<String> getPetNames(){
        return petNames;
    }

    class petHolder extends RecyclerView.ViewHolder{
        private ImageView imagePet;
        private TextView namePet;
        private TextView typePet;

        public petHolder(View itemView){
            super(itemView);
            imagePet = itemView.findViewById(R.id.PetImage);
            namePet = itemView.findViewById(R.id.PetName);
            typePet = itemView.findViewById(R.id.PetType);
        }
    }
}
