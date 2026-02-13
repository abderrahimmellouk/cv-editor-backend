package com.example.cv.cveditor.dto;

import lombok.Data;

@Data
public class LangueDTO {
    private String nom;
    private String niveau; // "DEBUTANT", "INTERMEDIAIRE", "AVANCE", "COURANT"
    private Long cvId;
}