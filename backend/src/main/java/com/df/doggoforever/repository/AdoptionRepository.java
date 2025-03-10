package com.df.doggoforever.repository;

import com.df.doggoforever.model.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
    List<Adoption> findByUserId(Long userId);
    List<Adoption> findByDogId(Long dogId);
    Adoption findByUserIdAndDogId(Long userId, Long dogId);
}
