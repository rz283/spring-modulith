package com.example.springmodulith.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record OrderDto(
        @NotBlank(message = "customerName is required")
        String customername,
        @NotBlank(message = "customerEmail is required")
        String customerEmail,
        @Valid
        List<InventoryRequestDto> inventoryRequestDtoList
) {
}
