package com.infobip.assessment.url.shortener.controller.security.request;

public record LoginRequest(String accountId, String password) {
}