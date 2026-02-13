package com.example.cv.cveditor.repository;

import com.example.cv.cveditor.model.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CVRepository extends JpaRepository<CV, Long> {
    // Cette méthode filtrera automatiquement les CV par la colonne user_id
    List<CV> findByUserId(Long userId);

}