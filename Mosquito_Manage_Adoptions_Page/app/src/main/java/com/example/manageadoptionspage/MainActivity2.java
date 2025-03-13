package com.example.manageadoptionspage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private boolean isEditing = false;
    private ImageButton editButton;
    private Spinner statusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Back button functionality
        Button backButton = findViewById(R.id.button2);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Initialize buttons and spinner
        editButton = findViewById(R.id.imageButton3);
        statusSpinner = findViewById(R.id.spinner);

        // Setup Spinner with custom item layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.status_options, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Keeps dropdown style default
        statusSpinner.setAdapter(adapter);

        // Disable spinner initially (to prevent unwanted changes)
        statusSpinner.setEnabled(false);

        // Edit button functionality
        editButton.setOnClickListener(v -> toggleEditMode());
    }

    private void toggleEditMode() {
        isEditing = !isEditing;
        if (isEditing) {
            editButton.setImageResource(R.drawable.check_btn); // Change to check icon
            statusSpinner.setEnabled(true); // Enable spinner for editing
        } else {
            editButton.setImageResource(R.drawable.edit_btn); // Change back to pencil icon
            statusSpinner.setEnabled(false); // Disable spinner after editing
        }
    }
}
