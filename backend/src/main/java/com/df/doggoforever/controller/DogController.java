package com.df.doggoforever.controller;

import com.df.doggoforever.model.Dog;
import com.df.doggoforever.service.DogService;
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

    @PostMapping
    public Dog addDog(@RequestBody Dog dog) {
        return service.addDog(dog);
    }

    @PutMapping("/{id}")
    public Dog updateDog(@PathVariable Long id, @RequestBody Dog dog) {
        return service.updateDog(id, dog);
    }

    @DeleteMapping("/{id}")
    public void deleteDog(@PathVariable Long id) {
        service.deleteDog(id);
    }

    @PostMapping("/{id}/upload-dog-image")
    public Dog uploadDogImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        return service.saveDogImage(id, image);
    }
}
