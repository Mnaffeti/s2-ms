package com.example.reclamations.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Reclamation {
    @Id
    @GeneratedValue
    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date_resolution ;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date_soumission ;

    @Email
    @NotBlank
    private String email;

    @Enumerated(EnumType.STRING)
    private TypeReclamation  typeReclamation;

    @Enumerated(EnumType.STRING)
    private Statut  statut;

    // Ajout du champ userId pour la relation
    private String userId;


}