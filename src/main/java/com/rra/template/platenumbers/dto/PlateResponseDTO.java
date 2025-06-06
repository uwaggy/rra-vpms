package com.rra.template.platenumbers.dto;

import java.util.UUID;

public record PlateResponseDTO(
        UUID id,
        String plateNumber,
        String issuedDate
) {
}
