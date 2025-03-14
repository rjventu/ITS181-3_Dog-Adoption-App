package com.example.appdev3_project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminDogsViewPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_dogs_view_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        MyUtil.initializeNavBar(AdminDogsViewPage.this);

        // Initialize Views

        // Disable fields, radiobuttons

        // Configure edit button

        // Configure delete button

        // Get data from Intent

    }

    private void editMode(){
        // Enable fields, radiobuttons

        // Attach click listener to imageView

        // Change pencil icon to check

    }

    private void selectImage(){

        // select image from device gallery

    }

    private void saveDogDetails(){

        // set dog object's updated details

        // send updated dog to database

        // on success, return to AdminAdoptionsViewPage

    }

    private void deleteDogRecord(){

        // confirmation dialog

        // on yes, delete record from database

        // on success, return to AdminAdoptionsViewPage

    }
}
