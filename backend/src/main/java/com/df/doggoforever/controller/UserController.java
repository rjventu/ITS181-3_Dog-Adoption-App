package com.df.doggoforever.controller;

import com.df.doggoforever.model.User;
import com.df.doggoforever.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return service.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
    }

    @PostMapping("/{id}/upload-image")
    public User uploadUserImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        return service.saveUserImage(id, image);
    }
}
