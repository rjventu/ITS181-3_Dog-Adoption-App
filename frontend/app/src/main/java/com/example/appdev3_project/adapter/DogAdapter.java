package com.example.appdev3_project.adapter;

import com.example.appdev3_project.DogProfilePage;
import com.example.appdev3_project.R;
import com.example.appdev3_project.model.Dog;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogViewHolder> {

    private List<Dog> dogList;

    public DogAdapter(List<Dog> dogList) {
        this.dogList = dogList;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_card, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        Dog dog = dogList.get(position);
        holder.dogName.setText(dog.getName());
        holder.dogGender.setText("Gender: " + dog.getGender());
        holder.dogAge.setText("Age: " + dog.getAge() + " years");
        holder.dogVaccination.setText("Vaccinated: " + (dog.isVaccinated() ? "Yes" : "No"));
        holder.dogSterilization.setText("Sterilized: " + (dog.isSterilized() ? "Yes" : "No"));
        holder.dogImage.setImageResource(dog.getImageResId());

        // Set click listener to open DogProfilePage
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DogProfilePage.class);
            intent.putExtra("dog", dog);  // Send the Dog object
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    public static class DogViewHolder extends RecyclerView.ViewHolder {
        TextView dogName, dogGender, dogAge, dogVaccination, dogSterilization;
        ImageView dogImage;

        public DogViewHolder(View itemView) {
            super(itemView);
            dogName = itemView.findViewById(R.id.dog_name);
            dogGender = itemView.findViewById(R.id.dog_gender);
            dogAge = itemView.findViewById(R.id.dog_age);
            dogVaccination = itemView.findViewById(R.id.dog_vaccination);
            dogSterilization = itemView.findViewById(R.id.dog_sterilization);
            dogImage = itemView.findViewById(R.id.dog_image);
        }
    }
}
