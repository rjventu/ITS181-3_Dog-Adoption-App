package com.df.doggoforever.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactFormDTO {
    private String name;
    private String email;
    private String message;
}
