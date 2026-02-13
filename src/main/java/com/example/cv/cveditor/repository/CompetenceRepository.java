package com.example.cv.cveditor.repository;

import com.example.cv.cveditor.model.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    List<Competence> findByCvId(Long cvId);
    List<Competence> findByCvIdOrderByNomAsc(Long cvId);
    List<Competence> findByCvIdAndNiveauContainingIgnoreCase(Long cvId, String niveau);
}