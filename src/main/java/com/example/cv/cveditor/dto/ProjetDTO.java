package com.example.cv.cveditor.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProjetDTO {
    private String titre;
    private String description;
    private String technologies;
    private LocalDate dateRealisation;
    private String lien;
    private Long cvId;
}