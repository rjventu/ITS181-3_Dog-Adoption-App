package com.example.appdev3_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appdev3_project.model.Adoption;
import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.model.User;
import com.example.appdev3_project.service.AdoptionService;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class AdminAdoptionsViewPage extends AppCompatActivity {
    private Spinner statusSpinner;
    TextView name, address, phone, email, date, time,
                dogName, dogAge, dogGender, dogBio;
    ImageView dogImage;
    ImageButton editButton;
    Adoption adoption;
    User user;
    Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_adoptions_view_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        MyUtil.initializeNavBar(AdminAdoptionsViewPage.this);

        // Initialize views
        name = findViewById(R.id.admin_adoptions_view_name);
        address = findViewById(R.id.admin_adoptions_view_address);
        phone = findViewById(R.id.admin_adoptions_view_phone);
        email = findViewById(R.id.admin_adoptions_view_email);
        date = findViewById(R.id.admin_adoptions_view_date);
        time = findViewById(R.id.admin_adoptions_view_time);
        dogImage = findViewById(R.id.admin_adoptions_view_dog_image);
        dogName = findViewById(R.id.admin_adoptions_view_dog_name);
        dogAge = findViewById(R.id.admin_adoptions_view_dog_age);
        dogGender = findViewById(R.id.admin_adoptions_view_dog_gender);
        dogBio = findViewById(R.id.admin_adoptions_view_dog_description_text);

        // Initialize spinner
        statusSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.status_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        statusSpinner.setAdapter(adapter);
        statusSpinner.setEnabled(false);

        // Configure imageButton
        editButton = (ImageButton) findViewById(R.id.btn_admin_adoptions_view_edit);
        editButton.setOnClickListener(view -> editMode());

        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("adoption")) {
            adoption = (Adoption) intent.getSerializableExtra("adoption");
            user = (User) intent.getSerializableExtra("user");
            dog = (Dog) intent.getSerializableExtra("dog");

            // Display User details
            name.setText(user.getName());
            address.setText("Address: " + user.getAddress());
            phone.setText("Phone: " + user.getContact());
            email.setText("Email: " + user.getUsername());

            // Format submission date
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            // Display Adoption details
            date.setText("Date: " + adoption.getDatetime().format(dateFormatter));
            time.setText("Time: " + adoption.getDatetime().format(timeFormatter));

            // Display spinner status
            String status = adoption.getStatus();
            int statusIndex = 0;
            if (status.equalsIgnoreCase("Accepted")) {
                statusIndex = 1;
            } else if (status.equalsIgnoreCase("Rejected")) {
                statusIndex = 2;
            }
            statusSpinner.setSelection(statusIndex);

            // Display Dog details
            dogName.setText(dog.getName());
            dogAge.setText("Age: " + dog.getAge() + " years");
            dogGender.setText("Gender: " + dog.getGender());
            dogBio.setText(dog.getBio());

            // Load image using Glide
            Glide.with(this)
                    .load(MyUtil.getImgUrl(dog.getImg()))
                    .placeholder(R.drawable.default_dog)
                    .error(R.drawable.default_dog)
                    .into(dogImage);

        }

    }

    private void editMode() {
        editButton.setImageResource(R.drawable.check_btn);
        editButton.setOnClickListener(view -> saveAdoptionDetails());
        statusSpinner.setEnabled(true);
    }

    private void saveAdoptionDetails(){

        // updates adoption status
        String status = statusSpinner.getSelectedItem().toString();
        adoption.setStatus(status);

        AdoptionService adoptionService = new AdoptionService(this);
        adoptionService.updateAdoption(adoption.getId(), adoption, new AdoptionService.AdoptionUpdateCallback() {
            @Override
            public void onSuccess(Adoption newAdoption) {
                Toast.makeText(AdminAdoptionsViewPage.this, "Application updated successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AdminAdoptionsPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getApplicationContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
