package com.df.doggoforever.service;

import com.df.doggoforever.model.Adoption;
import com.df.doggoforever.repository.AdoptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdoptionService {

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DogService dogService;

    public List<Adoption> getAllAdoptions() {
        return adoptionRepository.findAll();
    }

    public Adoption getAdoptionById(Long id) {
        return adoptionRepository.findById(id).orElse(null);
    }

    public List<Adoption> getAdoptionsByUserId(Long userId) {
        return adoptionRepository.findByUserId(userId);
    }

    public List<Adoption> getAdoptionsByDogId(Long dogId) {
        return adoptionRepository.findByDogId(dogId);
    }

    public Adoption addAdoption(Long userId, Long dogId, Adoption adoption) {

        adoption.setUser(userService.getUserById(userId));
        adoption.setDog(dogService.getDogById(dogId));
        adoption.setStatus("Pending");
        adoption.setDatetime(LocalDateTime.now());

        return adoptionRepository.save(adoption);
    }

    public Adoption updateAdoption(Long id, Adoption adoption) {
        Adoption existingAdoption = adoptionRepository.findById(id).orElse(null);
        if (existingAdoption != null) {
            existingAdoption.setStatus(adoption.getStatus());
            return adoptionRepository.save(existingAdoption);
        }
        return null;
    }

    public void deleteAdoption(Long id) {
        adoptionRepository.deleteById(id);
    }

    public void deleteAdoptionByUserAndDog(Long userId, Long dogId) {
        Adoption adoption = adoptionRepository.findByUserIdAndDogId(userId, dogId);
        if (adoption != null) {
            adoptionRepository.delete(adoption);
        }
    }


}
