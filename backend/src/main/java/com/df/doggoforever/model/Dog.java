package com.df.doggoforever.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="dog")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String gender;
    private boolean vacc;
    private boolean ster;
    private String img;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Adoption> adoptions = new HashSet<>();

}


