package com.df.doggoforever.controller;

import com.df.doggoforever.model.Adoption;
import com.df.doggoforever.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/adoptions")
public class AdoptionController {
    
    @Autowired
    private AdoptionService service;

    @GetMapping
    public List<Adoption> getAllAdoptions() {
        return service.getAllAdoptions();
    }

    @GetMapping("/exists")
    public boolean checkIfAdoptionExists(@RequestParam Long userId, @RequestParam Long dogId) {
        return service.adoptionExists(userId, dogId);
    }

    @GetMapping("/{id}")
    public Adoption getAdoptionById(@PathVariable Long id) {
        return service.getAdoptionById(id);
    }

    @GetMapping("/user/{id}")
    public List<Adoption> getAdoptionsByUserId(@PathVariable Long id) {
        return service.getAdoptionsByUserId(id);
    }

    @GetMapping("/dog/{id}")
    public List<Adoption> getAdoptionsByDogId(@PathVariable Long id) {
        return service.getAdoptionsByDogId(id);
    }

    @PostMapping("/{userId}/{dogId}")
    public Adoption addAdoption(@PathVariable Long userId, @PathVariable Long dogId, @RequestBody Adoption adoption) {
        return service.addAdoption(userId, dogId, adoption);
    }

    @PutMapping("/{id}")
    public Adoption updateAdoption(@PathVariable Long id, @RequestBody Adoption adoption) {
        return service.updateAdoption(id, adoption);
    }

    @DeleteMapping("/user/{userId}/dog/{dogId}")
    public ResponseEntity<Void> deleteAdoptionByUserAndDog(@PathVariable Long userId, @PathVariable Long dogId) {
        service.deleteAdoptionByUserAndDog(userId, dogId);
        return ResponseEntity.noContent().build();
    }

}
