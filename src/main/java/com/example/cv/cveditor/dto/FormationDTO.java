package com.example.cv.cveditor.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FormationDTO {
    private String diplome;
    private String etablissement;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String description;
    private Long cvId; // Référence au CV
}