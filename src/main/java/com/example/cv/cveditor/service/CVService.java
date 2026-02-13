package com.example.cv.cveditor.service;

import com.example.cv.cveditor.model.*;
import com.example.cv.cveditor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CVService {

    @Autowired private CVRepository cvRepository;
    @Autowired private FormationRepository formationRepository;
    @Autowired private ExperienceRepository experienceRepository;
    @Autowired private StageRepository stageRepository;
    @Autowired private LangueRepository langueRepository;
    @Autowired private CompetenceRepository competenceRepository;
    @Autowired private ProjetRepository projetRepository;
    @Autowired private CertificatRepository certificatRepository;

    // Dans CVService.java
    @Transactional(readOnly = true)
    public List<CV> getCVsByUserId(Long userId) {
        List<CV> cvs = cvRepository.findByUserId(userId);
        for (CV cv : cvs) {
            // On initialise les listes pour chaque CV de la liste
            if (cv.getLangues() != null) cv.getLangues().size();
            if (cv.getFormations() != null) cv.getFormations().size();
        }
        return cvs;
    }
    // ========== CV ==========
    @Transactional
    public CV createCV(CV cv) {
        // On lie chaque élément de chaque liste au CV principal
        if (cv.getFormations() != null) {
            cv.getFormations().forEach(f -> f.setCv(cv));
        }
        if (cv.getExperiences() != null) {
            cv.getExperiences().forEach(e -> e.setCv(cv));
        }
        if (cv.getStages() != null) {
            cv.getStages().forEach(s -> s.setCv(cv));
        }
        if (cv.getLangues() != null) {
            cv.getLangues().forEach(l -> l.setCv(cv));
        }
        if (cv.getCompetences() != null) {
            cv.getCompetences().forEach(c -> c.setCv(cv));
        }
        if (cv.getProjets() != null) {
            cv.getProjets().forEach(p -> p.setCv(cv));
        }
        if (cv.getCertificats() != null) {
            cv.getCertificats().forEach(c -> c.setCv(cv));
        }

        // Maintenant JPA saura que cv_id doit être l'ID de ce CV
        return cvRepository.save(cv);
    }

    public List<CV> getAllCVs() {
        return cvRepository.findAll();
    }

    public CV getCVById(Long id) {
        return cvRepository.findById(id).orElse(null);
    }

    @Transactional
    public CV updateCV(Long id, CV cvDetails) {
        return cvRepository.findById(id).map(cv -> {
            // 1. Mise à jour des champs simples

            cv.setNom(cvDetails.getNom());
            cv.setPrenom(cvDetails.getPrenom());
            cv.setEmail(cvDetails.getEmail());
            cv.setTelephone(cvDetails.getTelephone());
            cv.setAdresse(cvDetails.getAdresse());
            cv.setDateNaissance(cvDetails.getDateNaissance());
            cv.setResume(cvDetails.getResume());
            if (cvDetails.getPhoto() != null) {
                cv.setPhoto(cvDetails.getPhoto());
            }

            // 2. GESTION DES LISTES (Pour éviter que les supprimés reviennent)
            // On ne remplace pas la liste, on la met à jour
            if (cv.getFormations() != null) {
                cv.getFormations().clear(); // On vide l'ancienne liste
                if (cvDetails.getFormations() != null) {
                    cvDetails.getFormations().forEach(f -> {
                        f.setCv(cv); // On lie au CV actuel
                        cv.getFormations().add(f); // On rajoute les nouvelles/modifiées
                    });
                }
            }
            if (cv.getExperiences() != null) {
                cv.getExperiences().clear(); // On vide les anciennes
                if (cvDetails.getExperiences() != null) {
                    cvDetails.getExperiences().forEach(e -> {
                        e.setCv(cv); // On lie au CV actuel
                        cv.getExperiences().add(e);
                    });
                }
            }
            // Dans la méthode updateCV principale :
            if (cv.getStages() != null) {
                cv.getStages().clear();
                if (cvDetails.getStages() != null) {
                    cvDetails.getStages().forEach(s -> {
                        s.setCv(cv);
                        cv.getStages().add(s);
                    });
                }
            }
            if (cv.getLangues() != null) {
                cv.getLangues().clear();
                if (cvDetails.getLangues() != null) {
                    cvDetails.getLangues().forEach(l -> {
                        l.setCv(cv);
                        cv.getLangues().add(l);
                    });
                }
            }
            if (cv.getCompetences() != null) {
                cv.getCompetences().clear(); // On vide pour éviter les doublons
                if (cvDetails.getCompetences() != null) {
                    cvDetails.getCompetences().forEach(c -> {
                        c.setCv(cv); // On relie au CV
                        cv.getCompetences().add(c);
                    });
                }
            }
            if (cv.getProjets() != null) {
                cv.getProjets().clear(); // On nettoie pour éviter les doublons
                if (cvDetails.getProjets() != null) {
                    cvDetails.getProjets().forEach(p -> {
                        p.setCv(cv); // On lie le projet au CV
                        cv.getProjets().add(p);
                    });
                }
            }
            // DANS LA MÉTHODE updateCV (Mise à jour globale) :
            if (cv.getCertificats() != null) {
                cv.getCertificats().clear(); // On vide les anciens
                if (cvDetails.getCertificats() != null) {
                    cvDetails.getCertificats().forEach(c -> {
                        c.setCv(cv); // On lie au CV
                        cv.getCertificats().add(c);
                    });
                }
            }
            return cvRepository.save(cv);
        }).orElseThrow(() -> new RuntimeException("CV non trouvé"));
    }
    public void deleteCV(Long id) {
        cvRepository.deleteById(id);
    }


    // ========== FORMATIONS ==========
    // Dans CVService.java
    @Transactional
    public Formation addFormationToCV(Long cvId, Formation formation) {
        try {
            System.out.println("🔍 Recherche CV ID: " + cvId);

            // 1. Trouver le CV
            CV cv = cvRepository.findById(cvId)
                    .orElseThrow(() -> new RuntimeException("CV non trouvé avec ID: " + cvId));

            System.out.println("✅ CV trouvé: " );

            // 2. Lier la formation au CV
            formation.setCv(cv);

            // 3. Sauvegarder
            Formation saved = formationRepository.save(formation);

            System.out.println("✅ Formation sauvegardée ID: " + saved.getId());
            return saved;

        } catch (Exception e) {
            System.err.println("❌ Erreur dans addFormationToCV:");
            e.printStackTrace();
            throw e; // Important pour voir l'erreur dans les logs
        }
    }

    public List<Formation> getFormationsByCVId(Long cvId) {
        return formationRepository.findByCvId(cvId);
    }

    @Transactional
    public Formation updateFormation(Long id, Formation details) {
        return formationRepository.findById(id).map(f -> {
            f.setDiplome(details.getDiplome());
            f.setEtablissement(details.getEtablissement());
            f.setDateDebut(details.getDateDebut());
            f.setDateFin(details.getDateFin());
            f.setDescription(details.getDescription());
            return formationRepository.save(f); // Écrase l'ancienne ligne avec les nouvelles infos
        }).orElse(null);
    }
    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }

    // ========== EXPÉRIENCES ==========
    @Transactional
    public Experience addExperienceToCV(Long cvId, Experience experience) {
        CV cv = getCVById(cvId);
        if (cv != null) {
            experience.setCv(cv);
            return experienceRepository.save(experience);
        }
        return null;
    }

    public List<Experience> getExperiencesByCVId(Long cvId) {
        return experienceRepository.findByCvId(cvId);
    }

    public Experience updateExperience(Long id, Experience details) {
        return experienceRepository.findById(id).map(e -> {
                    e.setPoste(details.getPoste());
                    e.setEntreprise(details.getEntreprise());
                    e.setDateDebut(details.getDateDebut());
                    e.setDateFin(details.getDateFin());
                    e.setMissions(details.getMissions());
                    return experienceRepository.save(e);
                })
                .orElse(null);
    }

    @Transactional
    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
    }

    // ========== STAGES ==========
    @Transactional
    public Stage addStageToCV(Long cvId, Stage stage) {
        // 1. On récupère le CV bien frais depuis la DB
        CV cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new RuntimeException("CV non trouvé avec l'ID : " + cvId));

        // 2. On lie le stage au CV
        stage.setCv(cv);

        // 3. On sauvegarde
        return stageRepository.save(stage);
    }

    public List<Stage> getStagesByCVId(Long cvId) {
        return stageRepository.findByCvId(cvId);
    }

    @Transactional
    public Stage updateStage(Long id, Stage details) {
        return stageRepository.findById(id).map(s -> {
            s.setIntitule(details.getIntitule());
            s.setEntreprise(details.getEntreprise());
            s.setDuree(details.getDuree());
            s.setDescription(details.getDescription());
            return stageRepository.save(s);
        }).orElse(null);
    }



    public void deleteStage(Long id) {
        stageRepository.deleteById(id);
    }

    // ========== LANGUES ==========
    @Transactional
    public Langue addLangueToCV(Long cvId, Langue langue) {
        // On utilise findById(...).orElseThrow pour avoir une erreur claire si l'ID est faux
        CV cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new RuntimeException("❌ Erreur : CV non trouvé avec l'ID : " + cvId));

        langue.setCv(cv);
        return langueRepository.save(langue);
    }

    public List<Langue> getLanguesByCVId(Long cvId) {
        return langueRepository.findByCvId(cvId);
    }

    @Transactional
    public Langue updateLangue(Long id, Langue details) {
        return langueRepository.findById(id).map(l -> {
            l.setNom(details.getNom());
            l.setNiveau(details.getNiveau());
            return langueRepository.save(l);
        }).orElse(null);
    }




    public void deleteLangue(Long id) {
        langueRepository.deleteById(id);
    }

    // ========== COMPÉTENCES ==========
    @Transactional
    public Competence addCompetenceToCV(Long cvId, Competence competence) {
        CV cv = getCVById(cvId);
        if (cv != null) {
            competence.setCv(cv);
            return competenceRepository.save(competence);
        }
        return null;
    }

    public List<Competence> getCompetencesByCVId(Long cvId) {
        return competenceRepository.findByCvId(cvId);
    }

    @Transactional
    public Competence updateCompetence(Long id, Competence details) {
        return competenceRepository.findById(id).map(c -> {
            c.setNom(details.getNom());
            c.setNiveau(details.getNiveau());
            return competenceRepository.save(c);
        }).orElse(null);
    }




    public void deleteCompetence(Long id) {
        competenceRepository.deleteById(id);
    }

    // ========== PROJETS ==========
    @Transactional
    public Projet addProjetToCV(Long cvId, Projet projet) {
        CV cv = getCVById(cvId);
        if (cv != null) {
            projet.setCv(cv);
            return projetRepository.save(projet);
        }
        return null;
    }

    public List<Projet> getProjetsByCVId(Long cvId) {
        return projetRepository.findByCvId(cvId);
    }

    @Transactional
    public Projet updateProjet(Long id, Projet pDetails) {
        return projetRepository.findById(id).map(p -> {
            p.setTitre(pDetails.getTitre());
            p.setDescription(pDetails.getDescription());
            p.setTechnologies(pDetails.getTechnologies());
            p.setDateRealisation(pDetails.getDateRealisation());
            p.setLien(pDetails.getLien());
            return projetRepository.save(p);
        }).orElse(null);
    }

// DANS LA MÉTHODE updateCV (Mise à jour globale) :


    public void deleteProjet(Long id) {
        projetRepository.deleteById(id);
    }

    // ========== CERTIFICATS ==========
    @Transactional
    public Certificat addCertificatToCV(Long cvId, Certificat certificat) {
        CV cv = getCVById(cvId);
        if (cv != null) {
            certificat.setCv(cv);
            return certificatRepository.save(certificat);
        }
        return null;
    }

    public List<Certificat> getCertificatsByCVId(Long cvId) {
        return certificatRepository.findByCvId(cvId);
    }

    @Transactional
    public Certificat updateCertificat(Long id, Certificat cDetails) {
        return certificatRepository.findById(id).map(c -> {

            c.setOrganisme(cDetails.getOrganisme());
            c.setDateObtention(cDetails.getDateObtention());
            c.setNumeroCertificat(cDetails.getNumeroCertificat());
            c.setLien(cDetails.getLien());
            return certificatRepository.save(c);
        }).orElse(null);
    }



    public void deleteCertificat(Long id) {
        certificatRepository.deleteById(id);
    }
}