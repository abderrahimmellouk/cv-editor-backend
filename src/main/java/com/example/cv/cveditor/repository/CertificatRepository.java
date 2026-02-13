package com.example.cv.cveditor.repository;

import com.example.cv.cveditor.model.Certificat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CertificatRepository extends JpaRepository<Certificat, Long> {
    List<Certificat> findByCvId(Long cvId);
    List<Certificat> findByCvIdOrderByDateObtentionDesc(Long cvId);
    List<Certificat> findByCvIdAndOrganismeContainingIgnoreCase(Long cvId, String organisme);
}