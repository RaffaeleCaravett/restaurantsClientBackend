package com.example.orders.payloads.errors;

import java.util.Date;

public record ErrorsDTO(String message, Date timestamp) {
}
