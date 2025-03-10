package com.df.doggoforever.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
public class ImageController {

    @GetMapping("/uploads/{imageName}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable String imageName) {
        String basePath = "src/main/resources/static/uploads/";
        Path imagePath = Paths.get(basePath + imageName);

        if (!Files.exists(imagePath)) {
            imagePath = Paths.get(basePath + "default-user.png");
        }

        FileSystemResource file = new FileSystemResource(imagePath);
        return ResponseEntity.ok(file);
    }

    @GetMapping("/uploads-dogs/{imageName}")
    public ResponseEntity<FileSystemResource> getDogImage(@PathVariable String imageName) {
        String basePath = "src/main/resources/static/uploads-dogs/";
        Path imagePath = Paths.get(basePath + imageName);

        if (!Files.exists(imagePath)) {
            imagePath = Paths.get(basePath + "default-dog.png");
        }

        FileSystemResource file = new FileSystemResource(imagePath);
        return ResponseEntity.ok(file);
    }
}
