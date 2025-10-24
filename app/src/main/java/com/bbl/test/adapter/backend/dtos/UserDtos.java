package com.bbl.test.adapter.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "name is required") String name,
        @NotBlank(message = "username is required") String username,
        @NotBlank(message = "email is required") @Email(message = "email must be valid") String email,
        String phone,
        String website
) {}

public record UpdateUserRequest(
        @NotBlank(message = "name is required") String name,
        @NotBlank(message = "username is required") String username,
        @NotBlank(message = "email is required") @Email(message = "email must be valid") String email,
        String phone,
        String website
) {}