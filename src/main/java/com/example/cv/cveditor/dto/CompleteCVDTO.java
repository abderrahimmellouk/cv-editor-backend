package com.example.cv.cveditor.dto;

import lombok.Data;
import java.util.List;

@Data
public class CompleteCVDTO {
    private CVDTO cv;
    private List<FormationDTO> formations;
    private List<ExperienceDTO> experiences;
    private List<StageDTO> stages;
    private List<LangueDTO> languages;
    private List<CompetenceDTO> competences;
    private List<ProjetDTO> projects;
    private List<CertificatDTO> certificats;
}