package com.example.notification.kafka;

import com.example.notification.email.EmailService;
import com.example.notification.kafka.reclamation.ReclamationConfirmation;
import com.example.notification.notification.Notification;
import com.example.notification.notification.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.notification.notification.NotificationType.RECLAMATION_CONFIRMATION;
import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationsConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "reclamation-topic")
    public void consumePaymentSuccessNotifications(ReclamationConfirmation reclamationConfirmation) throws MessagingException {
        log.info(format("Consuming the message from payment-topic Topic:: %s", reclamationConfirmation));
        repository.save(
                Notification.builder()
                        .type(RECLAMATION_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .reclamationConfirmation(reclamationConfirmation)
                        .build()
        );
        var customerName = reclamationConfirmation.userId() + " " + reclamationConfirmation.idReclamation();
        emailService.sendReclamationEmail(
                reclamationConfirmation.mail(),
                reclamationConfirmation.idReclamation(),
                reclamationConfirmation.userId()


        );
    }


}
