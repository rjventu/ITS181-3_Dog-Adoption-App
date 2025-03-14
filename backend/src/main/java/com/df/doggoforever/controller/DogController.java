package com.df.doggoforever.controller;

import com.df.doggoforever.model.Dog;
import com.df.doggoforever.service.DogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/dogs")
public class DogController {
    @Autowired
    private DogService service;

    @GetMapping
    public List<Dog> getAllDogs() {
        return service.getAllDogs();
    }

    @GetMapping("/{id}")
    public Dog getDogById(@PathVariable Long id) {
        return service.getDogById(id);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public Dog addDog(
            @RequestPart("dog") String dogJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        
        ObjectMapper objectMapper = new ObjectMapper();
        Dog dog;
        try {
            dog = objectMapper.readValue(dogJson, Dog.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }

        return service.addDogWithImage(dog, image);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public Dog updateDog(@PathVariable Long id, @RequestPart("dog") Dog dog, @RequestPart(value = "image", required = false) MultipartFile image) {
        System.out.println("Updating Dog ID: " + id);
        return service.updateDogWithImage(id, dog, image);
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable Long id) {
        service.deleteDog(id);
    }

}
