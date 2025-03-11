package com.example.appdev3_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdev3_project.R;
import com.example.appdev3_project.model.Adoption;

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
        holder.dogName.setText(adoption.getDog().getName());
        holder.dogAge.setText(adoption.getDog().getAge());
        holder.dogGender.setText(adoption.getDog().getGender());
        holder.dogStatus.setText(adoption.getStatus());
    }

    @Override
    public int getItemCount() {
        return adoptionsList.size();
    }

    static class AdoptionViewHolder extends RecyclerView.ViewHolder {
        TextView dogName, dogAge, dogGender, dogStatus;

        public AdoptionViewHolder(@NonNull View itemView) {
            super(itemView);
            dogName = itemView.findViewById(R.id.content_dog_name);
            dogAge = itemView.findViewById(R.id.content_dog_age);
            dogGender = itemView.findViewById(R.id.content_dog_gender);
            dogStatus = itemView.findViewById(R.id.content_dog_status);
        }
    }
}
