package com.example.reclamations.Services;


import com.example.reclamations.DTO.ReclamationStatistiquesDTO;
import com.example.reclamations.DTO.UserDTO;
import com.example.reclamations.Entities.Reclamation;
import com.example.reclamations.Entities.Statut;
import com.example.reclamations.Kafka.ReclamationConfirmation;
import com.example.reclamations.Kafka.ReclamationProducer;
import com.example.reclamations.Repositories.ReclamationRepository;
import com.example.reclamations.feignclients.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReclamationService {

    private final ReclamationProducer reclamationProducer;

    @Autowired
    private ReclamationRepository reclamationRepository;

    private UserFeignClient userFeignClient;




    public ReclamationService(ReclamationProducer reclamationProducer, ReclamationRepository reclamationRepository, UserFeignClient userFeignClient) {
        this.reclamationProducer = reclamationProducer;
        this.reclamationRepository = reclamationRepository;

        this.userFeignClient = userFeignClient;
    }


    public Reclamation createReclamation(Reclamation reclamation) {
        // Vérifier si l'utilisateur existe
        if (reclamation.getUserId() != null) {
            ResponseEntity<Boolean> response = userFeignClient.existsById(reclamation.getUserId());
            if (response.getBody() == null || !response.getBody()) {
                throw new IllegalArgumentException("Utilisateur avec ID " + reclamation.getUserId() + " n'existe pas.");
            }

            // Optionnel : récupérer les détails de l'utilisateur pour vérifier l'email
            ResponseEntity<UserDTO> userResponse = userFeignClient.findById(reclamation.getUserId());
            if (userResponse.getBody() != null) {
                // Utiliser l'email de l'utilisateur si non fourni
                if (reclamation.getEmail() == null || reclamation.getEmail().isEmpty()) {
                    reclamation.setEmail(userResponse.getBody().getEmail());
                }
            }
        }

        if (reclamation.getDate_soumission() == null) {
            reclamation.setDate_soumission(LocalDate.now());
        }

        Reclamation savedReclamation = reclamationRepository.save(reclamation);
        reclamationProducer.sendReclamation(new ReclamationConfirmation(savedReclamation.getEmail(),savedReclamation.getId(),
                        savedReclamation.getUserId())
                );



        return savedReclamation;
    }

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    public Reclamation getReclamationById(int id) {
        return reclamationRepository.findById(id).orElse(null);
    }

    public Reclamation updateReclamation(int id, Reclamation newReclamation) {
        if (reclamationRepository.findById(id).isPresent()) {

            Reclamation existingReclamation = reclamationRepository.findById(id).get();
            existingReclamation.setTypeReclamation(newReclamation.getTypeReclamation());
            existingReclamation.setDate_resolution(newReclamation.getDate_resolution());
            existingReclamation.setDate_soumission(newReclamation.getDate_soumission());
            existingReclamation.setStatut(newReclamation.getStatut());
            existingReclamation.setEmail(newReclamation.getEmail());

            return reclamationRepository.save(existingReclamation);
        } else
            return null;
    }

    public void deleteReclamation(long id) {
        if (reclamationRepository.existsById(id)) {
            reclamationRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Réclamation avec ID " + id + " introuvable.");
        }
    }

    public List<Reclamation> getReclamationByStatut(String statut) {
        return reclamationRepository.findByStatutContainingIgnoreCase(statut);

    }

    public List<Reclamation> getAllSortedByTypeAsc() {
        return reclamationRepository.findAll(Sort.by(Sort.Direction.ASC, "typeReclamation"));
    }

    public List<Reclamation> getAllSortedByTypeDesc() {
        return reclamationRepository.findAll(Sort.by(Sort.Direction.DESC, "typeReclamation"));
    }

    public ReclamationStatistiquesDTO getStatistiquesAvancees() {
        List<Reclamation> all = reclamationRepository.findAll();

        long total = all.size();

        Map<Statut, Long> parStatut = all.stream()
                .collect(Collectors.groupingBy(Reclamation::getStatut, Collectors.counting()));

        Map<Statut, Double> pourcentageParStatut = parStatut.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (entry.getValue() * 100.0) / total
                ));

        return new ReclamationStatistiquesDTO(total, parStatut, pourcentageParStatut);
    }

    // Méthode pour obtenir toutes les réclamations d'un utilisateur
    public List<Reclamation> getReclamationsByUserId(String userId) {
        // Vérifier si l'utilisateur existe avant de chercher les réclamations
        ResponseEntity<Boolean> response = userFeignClient.existsById(userId);
        if (response.getBody() == null || !response.getBody()) {
            throw new IllegalArgumentException("Utilisateur avec ID " + userId + " n'existe pas.");
        }

        // Ici, vous auriez besoin d'ajouter une méthode dans le repository pour chercher par userId
        // return reclamationRepository.findByUserId(userId);

        // Pour l'instant, on filtre manuellement
        return reclamationRepository.findAll().stream()
                .filter(r -> userId.equals(r.getUserId()))
                .collect(Collectors.toList());
    }


}
