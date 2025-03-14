package com.example.appdev3_project;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.service.DogService;

import java.io.File;

import android.Manifest;

public class AdminDogsAddPage extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 100;
    TextView addMessage;
    EditText dogName, dogAge, dogBio;
    RadioGroup genderGroup, vaccGroup, sterGroup;
    RadioButton dogGenderMale, dogGenderFemale, dogVacc, dogNotVacc, dogSter, dogNotSter;
    ImageView dogImage;
    ImageButton checkButton;
    DogService dogService;
    Uri selectedImageUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_dogs_add_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        MyUtil.initializeNavBar(AdminDogsAddPage.this);

        // Initialize Views
        dogName = findViewById(R.id.admin_dogs_add_dog_name);
        dogAge = findViewById(R.id.admin_dogs_add_dog_age);
        dogBio = findViewById(R.id.admin_dogs_add_dog_description);
        dogGenderMale = findViewById(R.id.radioButton_gender_male);
        dogGenderFemale = findViewById(R.id.radioButton_gender_female);
        dogVacc = findViewById(R.id.radioButton_vacc_yes);
        dogNotVacc = findViewById(R.id.radioButton_vacc_no);
        dogSter = findViewById(R.id.radioButton_ster_yes);
        dogNotSter = findViewById(R.id.radioButton_ster_no);
        dogImage = findViewById(R.id.admin_dogs_add_dog_image);
        addMessage = findViewById(R.id.admin_dogs_add_image_message);
        checkButton = findViewById(R.id.btn_admin_dogs_add_check);
        genderGroup = findViewById(R.id.radioGroup_gender);
        vaccGroup = findViewById(R.id.radioGroup_vacc);
        sterGroup = findViewById(R.id.radioGroup_ster);

        // initialize services
        dogService = new DogService(this);

        // Configure dogImage listener
        dogImage.setOnClickListener(view -> requestStoragePermission());

        // Configure check button
        checkButton.setOnClickListener(view -> saveDogRecord());

    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_STORAGE_PERMISSION);
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
            } else {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                Glide.with(this).load(selectedImageUri).into(dogImage);
                addMessage.setVisibility(View.GONE);
            }
        }
    }

    private String getRealPathFromURI(Uri uri) {
        String filePath = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Android 10+
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    filePath = cursor.getString(index);
                }
            }
        } else { // For Android 9 and below
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                filePath = cursor.getString(columnIndex);
                cursor.close();
            }
        }
        return filePath;
    }

    private void saveDogRecord(){

        // Validate inputs
        String name = dogName.getText().toString().trim();
        String ageText = dogAge.getText().toString().trim();
        String bio = dogBio.getText().toString().trim();
        boolean isMale = dogGenderMale.isChecked();
        boolean isVaccinated = dogVacc.isChecked();
        boolean isSterilized = dogSter.isChecked();

        if (name.isEmpty() || ageText.isEmpty() || bio.isEmpty() || selectedImageUri == null ||
                genderGroup.getCheckedRadioButtonId() == -1 || vaccGroup.getCheckedRadioButtonId() == -1 ||
                sterGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageText);
        String gender = isMale ? "Male" : "Female";

        // Create dog object and set details
        Dog dog = new Dog();
        dog.setName(name);
        dog.setAge(age);
        dog.setGender(gender);
        dog.setVaccination(isVaccinated);
        dog.setSterilization(isSterilized);
        dog.setBio(bio);

        // create image file
        File imageFile = new File(getRealPathFromURI(selectedImageUri));

        // send dog and image to database
        dogService.addDog(dog, imageFile, success -> {
            if (success) {
                Toast.makeText(this, "Dog added successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminDogsAddPage.this, AdminDogsPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to add dog", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
