package com.example.cv.cveditor.controller;

import com.example.cv.cveditor.model.User;
import com.example.cv.cveditor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // <-- TRÈS IMPORTANT : Ne pas oublier !
@RequestMapping("/api/auth") // <-- Définit le début de l'URL
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register") // <-- Définit la fin de l'URL
    public ResponseEntity<User> register(@RequestBody User user) {
        System.out.println("📥 Requête reçue pour l'email : " + user.getEmail());
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        // On cherche l'utilisateur par email et password
        User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (user != null) {
            System.out.println("✅ Connexion réussie pour : " + user.getEmail());
            return ResponseEntity.ok(user); // On renvoie l'utilisateur avec son ID
        } else {
            System.out.println("❌ Échec de connexion pour : " + loginRequest.getEmail());
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }
    }
}