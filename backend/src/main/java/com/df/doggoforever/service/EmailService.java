package com.df.doggoforever.service;

import com.df.doggoforever.dtos.ApplicationDTO;
import com.df.doggoforever.dtos.ContactFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendContactEmail(ContactFormDTO contactForm) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("doggoforever2024@gmail.com");
        message.setSubject("New Inquiry from " + contactForm.getName());
        message.setText("Sender Name: " + contactForm.getName() + "\n"
                + "Sender Email: " + contactForm.getEmail() + "\n"
                + "Message:\n" + contactForm.getMessage());
        mailSender.send(message);
    }

    public void sendApplicationEmail(ApplicationDTO application) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("doggoforever2024@gmail.com");
        message.setSubject("New Adoption Application for " + application.getDogName());
        message.setText("Applicant Details\n"
                + "Name: " + application.getUserName() + "\n"
                + "Email: " + application.getUserEmail() + "\n"
                + "Contact Number: " + application.getContactNumber() + "\n"
                + "Address: " + application.getAddress() + "\n\n"
                + "Dog Name: " + application.getDogName());
        mailSender.send(message);
    }
}
