package com.example.cv.cveditor.controller;

import com.example.cv.cveditor.model.CV;
import com.example.cv.cveditor.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/cvs")
@CrossOrigin(origins = "http://localhost:4200")
public class CVController {

    @Autowired
    private CVService cvService;

    // GET ALL CVs
    // GET CVs OF CONNECTED USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CV>> getCVsByUser(@PathVariable Long userId) {
        try {
            List<CV> cvs = cvService.getCVsByUserId(userId);
            System.out.println("📋 Renvoi de " + cvs.size() + " CVs pour l'utilisateur ID: " + userId);

            for (CV cv : cvs) {
                if (cv.getPhoto() != null) {
                    cv.setPhotoBase64(Base64.getEncoder().encodeToString(cv.getPhoto()));
                }
            }
            return ResponseEntity.ok(cvs);
        } catch (Exception e) {
            System.err.println("❌ Erreur GET /cvs/user/" + userId + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET CV BY ID
    @GetMapping("/{id}")
    public ResponseEntity<CV> getCVById(@PathVariable Long id) {
        try {
            CV cv = cvService.getCVById(id);
            if (cv != null) {
                // Si une photo existe en base de données (format binaire)
                if (cv.getPhoto() != null && cv.getPhoto().length > 0) {
                    // On convertit les bytes en Base64
                    String base64Image = Base64.getEncoder().encodeToString(cv.getPhoto());

                    // On remplit le champ @Transient (photoBase64) avec le préfixe HTML
                    cv.setPhotoBase64("data:image/png;base64," + base64Image);
                }
                return ResponseEntity.ok(cv);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }}
    // CREATE CV
    @PostMapping
    public ResponseEntity<CV> createCV(
            @RequestPart("cv") CV cv,  // Partie JSON du CV
            @RequestPart(value = "photo", required = false) MultipartFile photoFile) {  // Fichier image, optionnel
        try {
            if (photoFile != null && !photoFile.isEmpty()) {
                cv.setPhoto(photoFile.getBytes());  // Convertir le fichier en bytes
                System.out.println("📸 Photo uploadée: " + photoFile.getOriginalFilename());
            }
            CV created = cvService.createCV(cv);
            // Optionnel : Convertir photo en base64 pour la réponse
            if (created.getPhoto() != null) {
                created.setPhotoBase64(Base64.getEncoder().encodeToString(created.getPhoto()));
            }
            System.out.println("✅ CV créé ID: " + created.getId());
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IOException e) {
            System.err.println("❌ Erreur upload photo: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("❌ Erreur création CV: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // UPDATE CV
    @PutMapping("/{id}")
    public ResponseEntity<CV> updateCV(
            @PathVariable Long id,
            @RequestPart("cv") CV cvDetails,
            @RequestPart(value = "photo", required = false) MultipartFile photoFile) {
        try {
            // 1. Si une nouvelle photo est envoyée, on l'écrase
            if (photoFile != null && !photoFile.isEmpty()) {
                cvDetails.setPhoto(photoFile.getBytes());
            }

            // 2. Appel au service pour sauvegarder les modifs
            CV updatedCV = cvService.updateCV(id, cvDetails);

            System.out.println("✅ CV mis à jour en base ! ID: " + id);
            return ResponseEntity.ok(updatedCV);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }   // DELETE CV
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCV(@PathVariable Long id) {
        try {
            cvService.deleteCV(id);
            System.out.println("🗑️ CV supprimé ID: " + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println("❌ Erreur suppression CV ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}