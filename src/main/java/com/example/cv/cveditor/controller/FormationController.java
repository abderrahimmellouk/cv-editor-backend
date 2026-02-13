package com.example.cv.cveditor.controller;

import com.example.cv.cveditor.model.Formation;
import com.example.cv.cveditor.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/formations")
@CrossOrigin(origins = "http://localhost:4200")
public class FormationController {

    @Autowired
    private CVService cvService;

    // VERSION 1 : Avec cvId séparé
    @PostMapping("/with-cv-id")
    public ResponseEntity<Formation> createFormationWithCvId(
            @RequestBody Formation formation,
            @RequestParam Long cvId) {
        try {
            System.out.println("📥 Formation reçue pour CV ID: " + cvId);
            Formation saved = cvService.addFormationToCV(cvId, formation);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("❌ Erreur création formation: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // VERSION 2 : Avec JSON contenant cvId
    @PostMapping
    public ResponseEntity<Formation> createFormation(@RequestBody Map<String, Object> payload) {
        try {
            System.out.println("📥 Payload reçu: " + payload);

            // Extraire la formation du payload
            Formation formation = new Formation();
            formation.setDiplome((String) payload.get("diplome"));
            formation.setEtablissement((String) payload.get("etablissement"));
            formation.setDateDebut(java.time.LocalDate.parse((String) payload.get("dateDebut")));
            formation.setDateFin(java.time.LocalDate.parse((String) payload.get("dateFin")));
            formation.setDescription((String) payload.get("description"));

            // Extraire cvId
            Long cvId;
            if (payload.containsKey("cvId")) {
                cvId = ((Number) payload.get("cvId")).longValue();
            } else if (payload.containsKey("cv")) {
                Map<String, Object> cvMap = (Map<String, Object>) payload.get("cv");
                cvId = ((Number) cvMap.get("id")).longValue();
            } else {
                throw new RuntimeException("cvId non trouvé dans le payload");
            }

            System.out.println("💾 Création formation pour CV ID: " + cvId);
            Formation saved = cvService.addFormationToCV(cvId, formation);

            return new ResponseEntity<>(saved, HttpStatus.CREATED);

        } catch (Exception e) {
            System.err.println("❌ Erreur création formation: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET BY CV ID
    @GetMapping("/cv/{cvId}")
    public ResponseEntity<List<Formation>> getFormationsByCV(@PathVariable Long cvId) {
        try {
            List<Formation> formations = cvService.getFormationsByCVId(cvId);
            return new ResponseEntity<>(formations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
        try {
            Formation updated = cvService.updateFormation(id, formation);
            if (updated != null) {
                return new ResponseEntity<>(updated, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id) {
        try {
            cvService.deleteFormation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}