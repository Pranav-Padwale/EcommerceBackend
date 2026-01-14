package com.ecommerce.backend.lagacy.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequest(

    @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)
