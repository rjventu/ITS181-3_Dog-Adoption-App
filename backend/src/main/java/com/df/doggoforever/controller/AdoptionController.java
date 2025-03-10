package com.df.doggoforever.controller;

import com.df.doggoforever.model.Adoption;
import com.df.doggoforever.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public Adoption addAdoption(@RequestParam Long userId, @RequestParam Long dogId, @RequestBody Adoption adoption) {
        return service.addAdoption(userId, dogId, adoption);
    }

    @PutMapping("/{id}")
    public Adoption updateAdoption(@PathVariable Long id, @RequestBody Adoption adoption) {
        return service.updateAdoption(id, adoption);
    }

    @DeleteMapping("/{id}")
    public void deleteAdoption(@PathVariable Long id) {
        service.deleteAdoption(id);
    }

    @DeleteMapping("/user/{userId}/dog/{dogId}")
    public void deleteAdoptionByUserAndDog(@PathVariable Long userId, @PathVariable Long dogId) {
        service.deleteAdoptionByUserAndDog(userId, dogId);
    }

}
