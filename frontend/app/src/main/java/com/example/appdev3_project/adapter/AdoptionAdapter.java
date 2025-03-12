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

import com.example.appdev3_project.DogProfilePage;
import com.example.appdev3_project.R;
import com.example.appdev3_project.model.Adoption;
import com.example.appdev3_project.model.Dog;

import java.util.List;

public class AdoptionAdapter extends RecyclerView.Adapter<AdoptionAdapter.AdoptionViewHolder> {

    private Context context;
    private List<Adoption> adoptionsList;

    public AdoptionAdapter(Context context, List<Adoption> adoptionsList) {
        this.context = context;
        this.adoptionsList = adoptionsList;
    }

    @NonNull
    @Override
    public AdoptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.applicant_adoption_card, parent, false);
        return new AdoptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdoptionViewHolder holder, int position) {
        Adoption adoption = adoptionsList.get(position);
        Dog dog = adoption.getDog();
        holder.dogName.setText(dog.getName());
        holder.dogAge.setText("Age: " + dog.getAge() + " years");
        holder.dogGender.setText("Gender: " + dog.getGender());
        holder.dogStatus.setText("Status: " + adoption.getStatus());
        holder.dogImage.setImageResource(R.drawable.default_dog);

        // Set click listener to open DogProfilePage
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DogProfilePage.class);
            intent.putExtra("dog", dog);  // Send the Dog object
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return adoptionsList.size();
    }

    static class AdoptionViewHolder extends RecyclerView.ViewHolder {
        TextView dogName, dogAge, dogGender, dogStatus;
        ImageView dogImage;

        public AdoptionViewHolder(@NonNull View itemView) {
            super(itemView);
            dogName = itemView.findViewById(R.id.content_dog_name);
            dogAge = itemView.findViewById(R.id.content_dog_age);
            dogGender = itemView.findViewById(R.id.content_dog_gender);
            dogStatus = itemView.findViewById(R.id.content_dog_status);
            dogImage = itemView.findViewById(R.id.content_dog_image);
        }
    }
}
