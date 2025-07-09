package com.example.notification.kafka.reclamation;


public record ReclamationConfirmation (
        String mail ,
        int idReclamation ,
        String userId
){


}
