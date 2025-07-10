package com.example.reclamations.kafka;

public record ReclamationConfirmation (
    String mail ,
    int idReclamation ,
    String userId
){


}
