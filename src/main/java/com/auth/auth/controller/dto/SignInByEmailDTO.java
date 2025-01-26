package com.auth.auth.controller.dto;

public record SignInByEmailDTO (
        String email,
        String password
) {
}
