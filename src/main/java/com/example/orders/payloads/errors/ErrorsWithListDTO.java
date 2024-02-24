package com.example.orders.payloads.errors;

import java.util.Date;
import java.util.List;

public record ErrorsWithListDTO(String message, Date timestamp, List<String> errorList) {
}
