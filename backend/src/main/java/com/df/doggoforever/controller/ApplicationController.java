package com.df.doggoforever.controller;

import com.df.doggoforever.dtos.ApplicationDTO;
import com.df.doggoforever.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> submitApplication(@RequestBody ApplicationDTO application) {
        emailService.sendApplicationEmail(application);
        System.out.println(application);
        return ResponseEntity.ok(Map.of("message",
                "Application submitted successfully! We have sent your details to the local vet. " +
                    "Please wait for further instructions."));
    }
}
