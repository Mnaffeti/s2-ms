package com.example.reclamations.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaReclamationTopicConfig {
    @Bean
    public NewTopic ReclamationTopic() {
        return TopicBuilder.name("reclamations-topic")
                .partitions(1)
                .build();
    }

}
