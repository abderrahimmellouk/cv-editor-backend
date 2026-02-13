package com.example.cv.cveditor.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ExperienceDTO {
    private String poste;
    private String entreprise;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String missions;
    private Long cvId;
}