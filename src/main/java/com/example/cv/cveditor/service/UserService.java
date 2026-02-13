package com.example.cv.cveditor.service;

import com.example.cv.cveditor.model.User;
import com.example.cv.cveditor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // Hachage du mot de passe en MD5
        user.setPassword(hashMD5(user.getPassword()));
        return userRepository.save(user);
    }

    private String hashMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du hachage MD5", e);
        }
    }
    public User login(String email, String password) {
        // 1. On hache le mot de passe reçu pour pouvoir le comparer à celui en base
        String hashedPassword = hashMD5(password);

        // 2. On cherche l'utilisateur qui a cet email ET ce mot de passe
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(hashedPassword))
                .orElse(null); // Retourne null si rien n'est trouvé
    }

// Ta fonction hashMD5 doit rester la même que pour le register
}