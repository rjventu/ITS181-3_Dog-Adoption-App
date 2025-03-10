package com.df.doggoforever.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private String userName;
    private String userEmail;
    private String contactNumber;
    private String address;
    private String dogName;
}
