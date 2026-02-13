package com.example.cv.cveditor.controller;

import com.example.cv.cveditor.model.Competence;
import com.example.cv.cveditor.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competences")
@CrossOrigin(origins = "http://localhost:4200")
public class CompetenceController {

    @Autowired
    private CVService cvService;

    @PostMapping
    public ResponseEntity<Competence> createCompetence(@RequestBody Competence competence) {
        if (competence.getCv() == null || competence.getCv().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Competence saved = cvService.addCompetenceToCV(competence.getCv().getId(), competence);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET BY CV ID
    @GetMapping("/cv/{cvId}")
    public ResponseEntity<List<Competence>> getCompetencesByCV(@PathVariable Long cvId) {
        List<Competence> competences = cvService.getCompetencesByCVId(cvId);
        return new ResponseEntity<>(competences, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Competence> update(@PathVariable Long id, @RequestBody Competence competence) {
        Competence updated = cvService.updateCompetence(id, competence);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cvService.deleteCompetence(id);
        return ResponseEntity.noContent().build();
    }
}