package com.example.cv.cveditor.controller;

import com.example.cv.cveditor.model.Experience;
import com.example.cv.cveditor.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
@CrossOrigin(origins = "http://localhost:4200")
public class ExperienceController {

    @Autowired
    private CVService cvService;

    // CREATE
    @PostMapping
    public ResponseEntity<Experience> createExperience(@RequestBody Experience experience) {
        Experience saved = cvService.addExperienceToCV(experience.getCv().getId(), experience);
        if (saved != null) {
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // GET BY CV ID
    @GetMapping("/cv/{cvId}")
    public ResponseEntity<List<Experience>> getExperiencesByCV(@PathVariable Long cvId) {
        List<Experience> experiences = cvService.getExperiencesByCVId(cvId);
        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Experience> update(@PathVariable Long id, @RequestBody Experience experience) {
        Experience updated = cvService.updateExperience(id, experience);
        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cvService.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }
}