package com.example.cv.cveditor.controller;

import com.example.cv.cveditor.model.Certificat;
import com.example.cv.cveditor.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificats")
@CrossOrigin(origins = "http://localhost:4200")
public class CertificatController {

    @Autowired
    private CVService cvService;

    // CREATE
    @PostMapping
    public ResponseEntity<Certificat> createCertificat(@RequestBody Certificat certificat) {
        if (certificat.getCv() == null || certificat.getCv().getId() == null) {
            System.err.println("❌ ID du CV manquant pour le certificat");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Certificat saved = cvService.addCertificatToCV(certificat.getCv().getId(), certificat);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cv/{cvId}")
    public ResponseEntity<List<Certificat>> getCertificatsByCV(@PathVariable Long cvId) {
        return ResponseEntity.ok(cvService.getCertificatsByCVId(cvId));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Certificat> update(@PathVariable Long id, @RequestBody Certificat certificat) {
        Certificat updated = cvService.updateCertificat(id, certificat);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cvService.deleteCertificat(id);
        return ResponseEntity.noContent().build();
    }
}