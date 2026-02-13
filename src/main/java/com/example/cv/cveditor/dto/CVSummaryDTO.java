package com.example.cv.cveditor.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CVSummaryDTO {
    private Long id;
    private String titre;
    private String nomComplet;
    private String email;
    private LocalDateTime dateCreation;
    private String previewImage; // URL de l'image de prévisualisation
}