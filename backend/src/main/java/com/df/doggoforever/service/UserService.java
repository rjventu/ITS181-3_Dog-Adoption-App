package com.df.doggoforever.service;

import com.df.doggoforever.model.User;
import com.df.doggoforever.repository.UserRepository;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Value("${file.upload-dir}")
    private String uploadDirPath;

    private Path uploadDir;

    @PostConstruct
    public void init() {
        this.uploadDir = Paths.get(uploadDirPath);
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) { return userRepository.findById(id).orElse(null); }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User addUser(User user) {
        user.setPassword(passwordService.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setContact(user.getContact());
            existingUser.setAddress(user.getAddress());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        String imageName = user.getImg();
        deleteUserImage(imageName);
        userRepository.deleteById(id);
    }

    public User saveUserImage(Long id, MultipartFile image) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        String imageName = user.getImg();
        deleteUserImage(imageName);

        String originalFilename = image.getOriginalFilename();
        String fileExtension = originalFilename != null && originalFilename.contains(".") ?
                originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";

        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        Path filePath = uploadDir.resolve(uniqueFilename);

        try {
            Files.copy(image.getInputStream(), filePath);
            user.setImg(uniqueFilename);
            return userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image file", e);
        }
    }

    private void deleteUserImage(String imageName){
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