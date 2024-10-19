package com.example.springmodulith.exception;

import org.springframework.http.HttpStatus;

public record Response(String message, HttpStatus status) {
}
