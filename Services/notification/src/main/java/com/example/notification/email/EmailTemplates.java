package com.example.notification.email;

import lombok.Getter;

public enum EmailTemplates {

    RECLAMATION_CONFIRMATION("reclamation-confirmation.html", "successfully processed"),
     ;

    @Getter
    private final String template;
    @Getter
    private final String subject;


    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
