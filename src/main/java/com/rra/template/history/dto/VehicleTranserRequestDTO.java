package com.rra.template.history.dto;

import com.rra.template.commons.validation.ValidPlateNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record VehicleTranserRequestDTO(
        @NotNull
        UUID vehicleId,

        @NotNull
        UUID newOwnerId,

        @NotBlank
        @ValidPlateNumber
        String newPlateNumber,

        @Positive
        Double purchasePrice
) {
}
