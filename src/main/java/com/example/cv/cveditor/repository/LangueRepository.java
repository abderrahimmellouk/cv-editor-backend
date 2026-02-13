package com.example.cv.cveditor.repository;

import com.example.cv.cveditor.model.Langue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LangueRepository extends JpaRepository<Langue, Long> {

    // 1. Trouver toutes les langues d'un CV
    List<Langue> findByCvId(Long cvId);

    // 2. Tri par niveau (Attention: avec String, le tri sera alphabétique A->Z)
    List<Langue> findByCvIdOrderByNiveauDesc(Long cvId);

    // 3. Correction ici : On remplace "Langue.NiveauLangue" par "String"
    List<Langue> findByCvIdAndNiveau(Long cvId, String niveau);
}