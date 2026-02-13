package com.example.cv.cveditor.controller;

import com.example.cv.cveditor.model.Stage;
import com.example.cv.cveditor.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stages")
@CrossOrigin(origins = "http://localhost:4200")
public class StageController {

    @Autowired
    private CVService cvService;

    // CREATE
    @PostMapping
    public ResponseEntity<Stage> createStage(@RequestBody Stage stage) {
        // Vérification de sécurité
        if (stage.getCv() == null || stage.getCv().getId() == null) {
            System.err.println("❌ Erreur: L'ID du CV est manquant dans la requête Stage");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Stage saved = cvService.addStageToCV(stage.getCv().getId(), stage);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET BY CV ID
    @GetMapping("/cv/{cvId}")
    public ResponseEntity<List<Stage>> getStagesByCV(@PathVariable Long cvId) {
        List<Stage> stages = cvService.getStagesByCVId(cvId);
        return new ResponseEntity<>(stages, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stage> update(@PathVariable Long id, @RequestBody Stage stage) {
        Stage updated = cvService.updateStage(id, stage);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cvService.deleteStage(id);
        return ResponseEntity.noContent().build();
    }
}