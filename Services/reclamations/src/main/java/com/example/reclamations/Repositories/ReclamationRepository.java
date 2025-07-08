package com.example.reclamations.Repositories;

import com.example.reclamations.Entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    Optional<Reclamation> findById(int id);

    void deleteById(int id);

    @Query("SELECT r FROM Reclamation r WHERE LOWER(r.statut) LIKE LOWER(CONCAT('%', :statut, '%'))")
    List<Reclamation> findByStatutContainingIgnoreCase(@Param("statut") String statut);

    // Ajout de la méthode pour trouver les réclamations par userId
    List<Reclamation> findByUserId(String userId);
}