package com.df.doggoforever.controller;

import com.df.doggoforever.dtos.ContactFormDTO;
import com.df.doggoforever.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendContactForm(@RequestBody ContactFormDTO contactForm) {
        emailService.sendContactEmail(contactForm);
        return ResponseEntity.ok(Map.of("message", "Email sent succesfully!"));
    }
}
