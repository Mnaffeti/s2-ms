package com.example.reclamations.Controllers;

import com.example.reclamations.DTO.ReclamationStatistiquesDTO;
import com.example.reclamations.Entities.Reclamation;
import com.example.reclamations.Services.ReclamationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reclamation")
public class ReclamationController {
    @Autowired
    private ReclamationService reclamationService;


    @PostMapping
    public ResponseEntity<Reclamation> createReclamation(@RequestBody Reclamation reclamation) {
        System.out.println("📥 Réclamation reçue : " + reclamation);
        Reclamation createdReclamation = reclamationService.createReclamation(reclamation);
        return new ResponseEntity<>(createdReclamation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Reclamation>> getAllReclamations() {
        return ResponseEntity.ok(reclamationService.getAllReclamations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable(required = true) int id) {
        System.out.println("🔍 Requête reçue avec ID : " + id);
        return ResponseEntity.ok(reclamationService.getReclamationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReclamation(
            @PathVariable int id,
            @RequestBody Reclamation updatedReclamation) {

        Reclamation updated = reclamationService.updateReclamation(id, updatedReclamation);

        if (updated != null) {
            return ResponseEntity.ok("✅ Réclamation mise à jour avec succès !");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Réclamation introuvable.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReclamation(@PathVariable int id) {
        try {
            reclamationService.deleteReclamation(id);
            return ResponseEntity.ok("✅ Réclamation supprimée avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Réclamation introuvable.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("💥 Erreur interne : " + e.getMessage());
        }
    }



    @GetMapping("/search")
    public ResponseEntity<List<Reclamation>> searchReclamations(@RequestParam String statut) {
        return ResponseEntity.ok(reclamationService.getReclamationByStatut(statut));
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Reclamation>> getAllReclamationsSorted(@RequestParam(defaultValue = "asc") String sort) {
        List<Reclamation> reclamations;
        if ("desc".equalsIgnoreCase(sort)) {
            reclamations = reclamationService.getAllSortedByTypeDesc(); // Tri décroissant
        } else {
            reclamations = reclamationService.getAllSortedByTypeAsc(); // Tri croissant (par défaut)
        }
        return new ResponseEntity<>(reclamations, HttpStatus.OK);
    }

    @GetMapping("/statistiques-avancees")
    public ResponseEntity<ReclamationStatistiquesDTO> getStatistiquesAvancees() {
        return ResponseEntity.ok(reclamationService.getStatistiquesAvancees());
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reclamation>> getReclamationsByUserId(@PathVariable String userId) {
        try {
            List<Reclamation> reclamations = reclamationService.getReclamationsByUserId(userId);
            return ResponseEntity.ok(reclamations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}