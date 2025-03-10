package com.example.managedogspage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setButtonClickListener(R.id.imageButton4);
        setButtonClickListener(R.id.imageButton8);
        setButtonClickListener(R.id.imageButton10);
        setButtonClickListener(R.id.imageButton13);

        // Add functionality for imageButton3 (add_dog_btn)
        ImageButton addDogBtn = findViewById(R.id.imageButton3);
        if (addDogBtn != null) {
            addDogBtn.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
            });
        }
    }

    private void setButtonClickListener(int buttonId) {
        ImageButton button = findViewById(buttonId);
        if (button != null) {
            button.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            });
        }
    }
}
