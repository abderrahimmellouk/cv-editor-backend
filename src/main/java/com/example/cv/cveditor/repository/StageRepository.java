package com.example.cv.cveditor.repository;

import com.example.cv.cveditor.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    List<Stage> findByCvId(Long cvId);
    List<Stage> findByCvIdOrderByEntrepriseAsc(Long cvId);
}