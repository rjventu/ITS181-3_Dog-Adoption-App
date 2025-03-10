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
                    return createSession(session, user.getUsername());
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
                    return createSession(session, user.getUsername());
                } else {
                    return ResponseEntity.badRequest().body(Map.of("message", "Please use the user sign-in form."));
                }
            }else{
                return ResponseEntity.badRequest().body(Map.of("message", "Incorrect password."));
            }
        }
        return ResponseEntity.badRequest().body(Map.of("message", "User not found."));
    }

    public ResponseEntity<?> createSession(HttpSession session, String username) {
        session.setAttribute("username", username);
        return ResponseEntity.ok(Map.of("sessionId", session.getId()));
    }

    @GetMapping("/session/get")
    public ResponseEntity<?> getSession(HttpSession session) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No user session active."));
        }

        User user  = userService.getUserByUsername(username);
        return ResponseEntity.ok(Map.of("user", user));
    }

    @GetMapping("/session/invalidate")
    public ResponseEntity<?> invalidateSession(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logout successful!"));
    }

}
