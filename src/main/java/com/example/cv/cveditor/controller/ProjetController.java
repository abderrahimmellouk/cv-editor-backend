package com.example.cv.cveditor.controller;

import com.example.cv.cveditor.model.Projet;
import com.example.cv.cveditor.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projets")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjetController {

    @Autowired
    private CVService cvService;

    // CREATE
    @PostMapping
    public ResponseEntity<Projet> createProjet(@RequestBody Projet projet) {
        // Vérification de sécurité pour l'objet CV imbriqué
        if (projet.getCv() == null || projet.getCv().getId() == null) {
            System.err.println("❌ ID du CV manquant pour le projet");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Projet saved = cvService.addProjetToCV(projet.getCv().getId(), projet);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cv/{cvId}")
    public ResponseEntity<List<Projet>> getProjetsByCV(@PathVariable Long cvId) {
        return ResponseEntity.ok(cvService.getProjetsByCVId(cvId));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Projet> update(@PathVariable Long id, @RequestBody Projet projet) {
        Projet updated = cvService.updateProjet(id, projet);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cvService.deleteProjet(id);
        return ResponseEntity.noContent().build();
    }
}