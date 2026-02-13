package com.example.cv.cveditor.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CVDTO {

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private LocalDate dateNaissance;
    private String photo;
    private String resume;
}