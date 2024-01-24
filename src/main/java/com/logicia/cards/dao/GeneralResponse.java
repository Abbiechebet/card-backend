package com.logicia.cards.dao;

import org.springframework.http.HttpStatus;

public class GeneralResponse {
    private HttpStatus status;
    private String description;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
