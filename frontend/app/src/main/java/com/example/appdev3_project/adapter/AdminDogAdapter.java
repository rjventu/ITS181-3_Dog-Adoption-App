package com.example.appdev3_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdev3_project.AdminDogsViewPage;
import com.example.appdev3_project.DogProfilePage;
import com.example.appdev3_project.MyUtil;
import com.example.appdev3_project.R;
import com.example.appdev3_project.model.Dog;

import java.util.List;

public class AdminDogAdapter extends RecyclerView.Adapter<AdminDogAdapter.AdminDogViewHolder>{

    private Context context;
    private List<Dog> dogList;

    public AdminDogAdapter(Context context, List<Dog> dogList) {
        this.context = context;
        this.dogList = dogList;
    }

    @NonNull
    @Override
    public AdminDogAdapter.AdminDogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_dog_card, parent, false);
        return new AdminDogAdapter.AdminDogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminDogAdapter.AdminDogViewHolder holder, int position) {
        Dog dog = dogList.get(position);
        holder.dogName.setText(dog.getName());
        holder.dogGender.setText("Gender: " + dog.getGender());
        holder.dogAge.setText("Age: " + dog.getAge() + " years");
        holder.dogVaccination.setText("Vaccinated: " + (dog.isVaccinated() ? "Yes" : "No"));
        holder.dogSterilization.setText("Sterilized: " + (dog.isSterilized() ? "Yes" : "No"));

        // Use Glide to load the image from URL
        Glide.with(holder.itemView.getContext())
                .load(MyUtil.getImgUrl(dog.getImg()))
                .placeholder(R.drawable.default_dog)
                .error(R.drawable.default_dog)
                .into(holder.dogImage);

        // Set click listener to open AdminDogsViewPage
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AdminDogsViewPage.class);
            intent.putExtra("dog", dog);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    public static class AdminDogViewHolder extends RecyclerView.ViewHolder {
        TextView dogName, dogGender, dogAge, dogVaccination, dogSterilization;
        ImageView dogImage;

        public AdminDogViewHolder(View itemView) {
            super(itemView);
            dogName = itemView.findViewById(R.id.admin_dogs_dog_name);
            dogGender = itemView.findViewById(R.id.admin_dogs_dog_gender);
            dogAge = itemView.findViewById(R.id.admin_dogs_dog_age);
            dogVaccination = itemView.findViewById(R.id.admin_dogs_dog_vacc);
            dogSterilization = itemView.findViewById(R.id.admin_dogs_dog_ster);
            dogImage = itemView.findViewById(R.id.admin_dogs_dog_image);
        }
    }
}
