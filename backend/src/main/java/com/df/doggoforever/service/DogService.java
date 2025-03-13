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

    public Dog addDog(Dog dog) {
        return dogRepository.save(dog);
    }

    public Dog updateDog(Long id, Dog dog) {
        Dog existingDog = dogRepository.findById(id).orElse(null);
        if (existingDog != null) {
            existingDog.setName(dog.getName());
            existingDog.setAge(dog.getAge());
            existingDog.setGender(dog.getGender());
            existingDog.setVacc(dog.isVacc());
            existingDog.setSter(dog.isSter());
            existingDog.setDescription(dog.getDescription());
            return dogRepository.save(existingDog);
        }
        return null;
    }

    public void deleteDog(Long id) {
        dogRepository.deleteById(id);
    }

    public Dog saveDogImage(Long id, MultipartFile image) {
        Dog dog = dogRepository.findById(id).orElse(null);
        if (dog == null) {
            throw new RuntimeException("User not found!");
        }

        String imageName = dog.getImg();
        deleteDogImage(imageName);

        String originalFilename = image.getOriginalFilename();
        String fileExtension = originalFilename != null && originalFilename.contains(".") ?
                originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg"; // Default to .jpg if no extension

        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        Path filePath = uploadDir.resolve(uniqueFilename);

        try {
            Files.copy(image.getInputStream(), filePath);
            dog.setImg(uniqueFilename);
            return dogRepository.save(dog);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image file", e);
        }
    }

    private void deleteDogImage(String imageName){
        if (imageName != null && !imageName.isEmpty() && !imageName.equals("default-user.png")) {
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
