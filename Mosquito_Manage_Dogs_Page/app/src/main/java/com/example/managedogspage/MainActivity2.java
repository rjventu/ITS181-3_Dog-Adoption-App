package com.example.managedogspage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private boolean isEditing = false; // Track the edit state
    private ImageButton editButton;

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

        // Back Button Logic
        Button backButton = findViewById(R.id.button2);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Edit Button Logic
        editButton = findViewById(R.id.imageButton3);
        editButton.setOnClickListener(v -> toggleEditMode());
    }

    private void toggleEditMode() {
        if (isEditing) {
            // Switch to pencil (edit) mode
            editButton.setImageResource(R.drawable.edit_btn);
        } else {
            // Switch to check (done) mode
            editButton.setImageResource(R.drawable.check_btn);
        }
        isEditing = !isEditing;
    }
}
