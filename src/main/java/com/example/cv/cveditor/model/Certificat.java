package com.example.cv.cveditor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "certificat")
public class Certificat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String organisme;

    @Column(name = "date_obtention")
    private LocalDate dateObtention;

    @Column(name = "numero_certificat")
    private String numeroCertificat;

    private String lien;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CV cv;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

      public String getOrganisme() { return organisme; }
    public void setOrganisme(String organisme) { this.organisme = organisme; }

    public LocalDate getDateObtention() { return dateObtention; }
    public void setDateObtention(LocalDate dateObtention) { this.dateObtention = dateObtention; }

    public String getNumeroCertificat() { return numeroCertificat; }
    public void setNumeroCertificat(String numeroCertificat) { this.numeroCertificat = numeroCertificat; }

    public String getLien() { return lien; }
    public void setLien(String lien) { this.lien = lien; }

    public CV getCv() { return cv; }
    public void setCv(CV cv) { this.cv = cv; }
}