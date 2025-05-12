package com.rra.template.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO (
    @NotBlank(message = "Field is required")
    @Email(message = "Enter a valid email")
    String email,

    @NotBlank(message="Password id required")
    String password

){

}
