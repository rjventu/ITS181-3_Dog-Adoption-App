package com.df.doggoforever.repository;

import com.df.doggoforever.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
