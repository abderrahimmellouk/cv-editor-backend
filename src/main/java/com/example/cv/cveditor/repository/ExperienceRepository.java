package com.example.cv.cveditor.repository;

import com.example.cv.cveditor.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByCvId(Long cvId);
    List<Experience> findByCvIdOrderByDateDebutDesc(Long cvId);
    List<Experience> findByCvIdAndDateFinIsNull(Long cvId); // Expériences en cours
}