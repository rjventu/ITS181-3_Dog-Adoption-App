package com.df.doggoforever.service;

import com.df.doggoforever.model.Dog;
import com.df.doggoforever.repository.DogRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class DogService {
    @Autowired
    private DogRepository dogRepository;

    @Value("${file.upload-dogs-dir}")
    private String uploadDirPath;

    private Path uploadDir;

    @PostConstruct
    public void init() {
        this.uploadDir = Paths.get(uploadDirPath);
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload dogs directory!", e);
        }
    }

    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    public Dog getDogById(Long id) {
        return dogRepository.findById(id).orElse(null);
    }

    public Dog addDogWithImage(Dog dog, MultipartFile image) {
        try {
            // save image first
            String originalFilename = image.getOriginalFilename();
            String fileExtension = originalFilename != null && originalFilename.contains(".") ?
                    originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadDir.resolve(uniqueFilename);
            
            Files.copy(image.getInputStream(), filePath);
            
            // save dog details with image filename
            dog.setImg(uniqueFilename);
            return dogRepository.save(dog);
    
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image file", e);
        }
    }

    public Dog updateDogWithImage(Long id, Dog updatedDog, MultipartFile image) {
        Dog existingDog = dogRepository.findById(id).orElse(null);
        existingDog.setName(updatedDog.getName());
        existingDog.setAge(updatedDog.getAge());
        existingDog.setGender(updatedDog.getGender());
        existingDog.setVacc(updatedDog.isVacc());
        existingDog.setSter(updatedDog.isSter());
        existingDog.setDescription(updatedDog.getDescription());
    
        if (image != null && !image.isEmpty()) {
            deleteDogImage(existingDog.getImg());
    
            String originalFilename = image.getOriginalFilename();
            String fileExtension = originalFilename != null && originalFilename.contains(".") ?
                    originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadDir.resolve(uniqueFilename);
    
            try {
                Files.copy(image.getInputStream(), filePath);
                existingDog.setImg(uniqueFilename);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store image file", e);
            }
        }
    
        return dogRepository.save(existingDog);
    }
    
    public void deleteDog(Long id) {
        Dog dog = dogRepository.findById(id).orElse(null);
        deleteDogImage(dog.getImg());
        dogRepository.deleteById(id);
    }    
    
    private void deleteDogImage(String imageName){
        if (imageName != null && !imageName.isEmpty() && !imageName.equals("default-dog.png")) {
            Path filePath = uploadDir.resolve(imageName);
            try {
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete image file", e);
            }
        }
    }

}
