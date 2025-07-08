package com.example.reclamations.DTO;

import com.example.reclamations.Entities.Statut;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class ReclamationStatistiquesDTO {
    private long totale;
    private Map<Statut, Long> parStatut;
    private Map<Statut, Double> pourcentageParStatut;
}
