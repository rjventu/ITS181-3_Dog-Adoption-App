package com.df.doggoforever.controller;

import com.df.doggoforever.model.User;
import com.df.doggoforever.service.AuthService;
import com.df.doggoforever.service.PasswordService;
import com.df.doggoforever.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody User user) {
        if(!authService.usernameIsTaken(user.getUsername())){

            // Set role to "USER" if not provided
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("USER");
            }

            userService.addUser(user);
            return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Email address is taken."));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> login(HttpSession session, @RequestBody User user) {
        User existingUser = authService.matchesUsername(user.getUsername());

        if (existingUser != null){
            if (passwordService.checkPassword(user.getPassword(), existingUser.getPassword())) {
                if(Objects.equals(existingUser.getRole(), "USER")){
                    return ResponseEntity.ok(existingUser); // return user object
                } else {
                    return ResponseEntity.badRequest().body(Map.of("message", "Please use the admin sign-in form."));
                }
            }else{
                return ResponseEntity.badRequest().body(Map.of("message", "Incorrect password."));
            }
        }
        return ResponseEntity.badRequest().body(Map.of("message", "User not found."));
    }

    @PostMapping("/sign-in-admin")
    public ResponseEntity<?> loginAdmin(HttpSession session, @RequestBody User user) {
        User existingUser = authService.matchesUsername(user.getUsername());

        if (existingUser != null){
            if (passwordService.checkPassword(user.getPassword(), existingUser.getPassword())) {
                if(Objects.equals(existingUser.getRole(), "ADMIN")){
                    return ResponseEntity.ok(existingUser); // return user object
                } else {
                    return ResponseEntity.badRequest().body(Map.of("message", "Please use the applicant sign-in form."));
                }
            }else{
                return ResponseEntity.badRequest().body(Map.of("message", "Incorrect password."));
            }
        }
        return ResponseEntity.badRequest().body(Map.of("message", "User not found."));
    }

}
