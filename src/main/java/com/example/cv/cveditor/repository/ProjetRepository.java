package com.example.cv.cveditor.repository;

import com.example.cv.cveditor.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    List<Projet> findByCvId(Long cvId);
    List<Projet> findByCvIdOrderByDateRealisationDesc(Long cvId);
    List<Projet> findByCvIdAndTechnologiesContainingIgnoreCase(Long cvId, String technologie);
}