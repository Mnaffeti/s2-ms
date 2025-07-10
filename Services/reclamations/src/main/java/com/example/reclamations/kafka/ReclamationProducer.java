package com.example.reclamations.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReclamationProducer {
    private final KafkaTemplate<String,ReclamationConfirmation> kafkaTemplate;
    public void sendReclamation(ReclamationConfirmation recConfirm){
        log.info("Sending message: {}", recConfirm);
        Message<ReclamationConfirmation> msg = MessageBuilder.withPayload(recConfirm).
                setHeader(KafkaHeaders.TOPIC,"reclamations-topic").build() ;
        kafkaTemplate.send(msg);
    }
}
