package com.example.cv.cveditor.controller;

import com.example.cv.cveditor.model.Langue;
import com.example.cv.cveditor.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/langues")
@CrossOrigin(origins = "http://localhost:4200")
public class LangueController {

    @Autowired
    private CVService cvService;

    // CREATE
    @PostMapping
    public ResponseEntity<Langue> createLangue(@RequestBody Langue langue) {
        try {
            // On vérifie si l'objet CV et son ID existent dans le JSON reçu
            if (langue.getCv() == null || langue.getCv().getId() == null) {
                System.err.println("❌ L'objet CV ou l'ID du CV est manquant");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Langue saved = cvService.addLangueToCV(langue.getCv().getId(), langue);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // 🔥 Regardez votre console Java (IntelliJ/Eclipse) !
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // GET BY CV ID
    @GetMapping("/cv/{cvId}")
    public ResponseEntity<List<Langue>> getLanguesByCV(@PathVariable Long cvId) {
        List<Langue> langues = cvService.getLanguesByCVId(cvId);
        return new ResponseEntity<>(langues, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Langue> update(@PathVariable Long id, @RequestBody Langue langue) {
        Langue updated = cvService.updateLangue(id, langue);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cvService.deleteLangue(id);
        return ResponseEntity.noContent().build();
    }
}