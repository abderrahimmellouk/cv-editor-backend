package com.example.cv.cveditor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "projet")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(length = 2000)
    private String description;

    private String technologies;

    @Column(name = "date_realisation")
    private LocalDate dateRealisation;

    private String lien;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CV cv;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTechnologies() { return technologies; }
    public void setTechnologies(String technologies) { this.technologies = technologies; }

    public LocalDate getDateRealisation() { return dateRealisation; }
    public void setDateRealisation(LocalDate dateRealisation) { this.dateRealisation = dateRealisation; }

    public String getLien() { return lien; }
    public void setLien(String lien) { this.lien = lien; }

    public CV getCv() { return cv; }
    public void setCv(CV cv) { this.cv = cv; }
}