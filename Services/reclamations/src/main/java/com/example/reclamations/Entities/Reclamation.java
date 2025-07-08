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
    @lombok.Setter
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

    // Getter et Setter pour le nouveau champ
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate_resolution() {
        return date_resolution;
    }

    public LocalDate getDate_soumission() {
        return date_soumission;
    }

    public void setDate_soumission(LocalDate date_soumission) {
        this.date_soumission = date_soumission;
    }

    public TypeReclamation getTypeReclamation() {
        return typeReclamation;
    }

    public void setTypeReclamation(TypeReclamation typeReclamation) {
        this.typeReclamation = typeReclamation;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

}