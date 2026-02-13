package com.example.cv.cveditor.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CertificatDTO {
    private String titre;
    private String organisme;
    private LocalDate dateObtention;
    private String numeroCertificat;
    private String lien;
    private Long cvId;
}