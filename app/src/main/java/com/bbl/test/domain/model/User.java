package com.bbl.test.domain.model;

public record User(
        Long id,
        String name,
        String username,
        String email,
        String phone,
        String website
) {}