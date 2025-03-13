package com.example.appdev3_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdev3_project.DogProfilePage;
import com.example.appdev3_project.MainActivity2;
import com.example.appdev3_project.MyUtil;
import com.example.appdev3_project.R;
import com.example.appdev3_project.model.Adoption;
import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.model.User;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminAdoptionAdapter extends RecyclerView.Adapter<AdminAdoptionAdapter.AdminAdoptionViewHolder> {

    private Context context;
    private List<Adoption> adoptionsList;

    public AdminAdoptionAdapter(Context context, List<Adoption> adoptionsList) {
        this.context = context;
        this.adoptionsList = adoptionsList;
    }

    @NonNull
    @Override
    public AdminAdoptionAdapter.AdminAdoptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_adoptions_card, parent, false);
        return new AdminAdoptionAdapter.AdminAdoptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdoptionAdapter.AdminAdoptionViewHolder holder, int position) {
        Adoption adoption = adoptionsList.get(position);
        Dog dog = adoption.getDog();
        User user = adoption.getUser();
        holder.dogName.setText(dog.getName());
        holder.adoptionName.setText("Applicant: " + user.getName());
        holder.adoptionStatus.setText("Status: " + adoption.getStatus());

        // Format submission date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        holder.adoptionDate.setText("Date: " + adoption.getDatetime().format(dateFormatter));
        holder.adoptionTime.setText("Time: " + adoption.getDatetime().format(timeFormatter));

        // Use Glide to load the image from URL
        Glide.with(holder.itemView.getContext())
                .load(MyUtil.getImgUrl(dog.getImg()))
                .placeholder(R.drawable.default_dog)
                .error(R.drawable.default_dog)
                .into(holder.dogImage);

        // Configure viewButton
        holder.viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity2.class);
            intent.putExtra("adoption", adoption);  // Send the Adoption object
            intent.putExtra("user", user);  // Send the User object
            intent.putExtra("dog", dog);  // Send the Dog object
            v.getContext().startActivity(intent);
        });

        // Configure editButton
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity2.class);
            intent.putExtra("adoption", adoption);  // Send the Adoption object
            intent.putExtra("user", user);  // Send the User object
            intent.putExtra("dog", dog);  // Send the Dog object
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return adoptionsList.size();
    }

    static class AdminAdoptionViewHolder extends RecyclerView.ViewHolder {
        TextView dogName, adoptionName, adoptionDate, adoptionTime, adoptionStatus;
        ImageView dogImage;
        ImageButton viewButton, editButton;

        public AdminAdoptionViewHolder(@NonNull View itemView) {
            super(itemView);
            dogName = itemView.findViewById(R.id.admin_adoptions_dog_name);
            adoptionName = itemView.findViewById(R.id.admin_adoptions_adoption_name);
            adoptionDate = itemView.findViewById(R.id.admin_adoptions_adoption_date);
            adoptionTime = itemView.findViewById(R.id.admin_adoptions_adoption_time);
            adoptionStatus = itemView.findViewById(R.id.admin_adoptions_adoption_status);
            dogImage = itemView.findViewById(R.id.admin_adoptions_dog_image);
            viewButton = itemView.findViewById(R.id.btn_admin_adoptions_view);
            editButton = itemView.findViewById(R.id.btn_admin_adoptions_edit);
        }
    }




}
