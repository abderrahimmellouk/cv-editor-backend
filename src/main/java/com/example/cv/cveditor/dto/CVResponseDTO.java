package com.example.cv.cveditor.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CVResponseDTO {
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private LocalDateTime dateCreation;

    // Statistiques
    private int nombreFormations;
    private int nombreExperiences;
    private int nombreCompetences;
}