package com.example.cv.cveditor.repository;

import com.example.cv.cveditor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par son email.
     * Très utile pour la connexion (Login) et pour vérifier
     * si un email est déjà utilisé lors de l'inscription.
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie si un email existe déjà dans la base de données.
     * Retourne true si l'email existe, false sinon.
     */
    Boolean existsByEmail(String email);
}