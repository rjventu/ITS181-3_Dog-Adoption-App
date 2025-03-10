package com.example.managedogspage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity3 extends AppCompatActivity {

    private EditText idEditText, nameEditText, ageEditText, genderEditText, descriptionEditText;
    private ImageButton check_btn, deldog_btn, imageButton;
    private Button backButton;
    private TextView addImageHint; // Hint for "Add Image"

    private boolean isEditing = false; // Tracks edit mode (initially false)
    private static final int PICK_IMAGE_REQUEST = 1; // Image picker request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        // Initialize EditTexts
        idEditText = findViewById(R.id.editTextText);
        nameEditText = findViewById(R.id.editTextText2);
        ageEditText = findViewById(R.id.editTextText3);
        genderEditText = findViewById(R.id.editTextText4);
        descriptionEditText = findViewById(R.id.editTextText5);

        // Initialize Buttons
        check_btn = findViewById(R.id.imageButton7);
        deldog_btn = findViewById(R.id.imageButton6);
        backButton = findViewById(R.id.button2);
        imageButton = findViewById(R.id.imageButton11);
        addImageHint = findViewById(R.id.addImageHint);

        setTextBlack();

        // Enable editing by default
        isEditing = true;
        setEditTextsEnabled(true);
        setImageSelectionEnabled(true);
        check_btn.setImageResource(R.drawable.check_btn);

        // Toggle between edit and view mode
        check_btn.setOnClickListener(v -> {
            if (isEditing) {
                setEditTextsEnabled(false);
                setImageSelectionEnabled(false);
                check_btn.setImageResource(R.drawable.edit_btn);
            } else {
                setEditTextsEnabled(true);
                setImageSelectionEnabled(true);
                check_btn.setImageResource(R.drawable.check_btn);
            }
            isEditing = !isEditing;
        });

        deldog_btn.setOnClickListener(v -> {
            idEditText.setText("");
            nameEditText.setText("");
            ageEditText.setText("");
            genderEditText.setText("");
            descriptionEditText.setText("");

            setEditTextsEnabled(true);
            setImageSelectionEnabled(true);
            check_btn.setImageResource(R.drawable.check_btn);
            isEditing = true;
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }


    // Function to enable or disable EditTexts
    private void setEditTextsEnabled(boolean enabled) {
        idEditText.setEnabled(enabled);
        nameEditText.setEnabled(enabled);
        ageEditText.setEnabled(enabled);
        genderEditText.setEnabled(enabled);
        descriptionEditText.setEnabled(enabled);
    }

    // Function to enable or disable Image Selection
    private void setImageSelectionEnabled(boolean enabled) {
        if (enabled) {
            imageButton.setOnClickListener(v -> openGallery());
            addImageHint.setOnClickListener(v -> openGallery());
        } else {
            imageButton.setOnClickListener(null);
            addImageHint.setOnClickListener(null);
        }
    }

    // Function to set text color to black for visibility
    private void setTextBlack() {
        idEditText.setTextColor(getResources().getColor(android.R.color.black));
        nameEditText.setTextColor(getResources().getColor(android.R.color.black));
        ageEditText.setTextColor(getResources().getColor(android.R.color.black));
        genderEditText.setTextColor(getResources().getColor(android.R.color.black));
        descriptionEditText.setTextColor(getResources().getColor(android.R.color.black));
    }

    // Function to open gallery for image selection
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handle image selection result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageButton.setImageBitmap(bitmap);
                addImageHint.setVisibility(View.GONE); // Hide hint when image is selected
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
