package com.example.reclamations.Services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendReclamationConfirmation(String toEmail, int reclamationId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Confirmation de la création de votre réclamation");

        String body = "Bonjour,\n\n"
                + "Nous vous confirmons la bonne réception de votre réclamation numéro #" + reclamationId + ".\n"
                + "Notre équipe traitera " +
                "votre demande dans les plus brefs délais.\n\n"
                + "Merci de votre confiance.\n"
                + "Cordialement,\n"
                + "L'équipe du service client.";

        message.setText(body);
        mailSender.send(message);
    }


    /*public void sendStatusUpdate(String toEmail, Long reclamationId, String newStatus) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Mise à jour de statut");
        message.setText("Le statut de votre réclamation #" + reclamationId + " est maintenant : " + newStatus);
        mailSender.send(message);
    }*/
}