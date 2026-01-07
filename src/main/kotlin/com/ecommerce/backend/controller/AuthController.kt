package com.ecommerce.backend.controller

import com.ecommerce.backend.dto.AuthResponse
import com.ecommerce.backend.dto.LoginRequest
import com.ecommerce.backend.dto.RegisterRequest
import com.ecommerce.backend.security.JwtService
import com.ecommerce.backend.service.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
    private val jwtService: JwtService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest) {
        authService.register(
            name = request.name,
            email = request.email,
            rawPassword = request.password
        )
    }


    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): AuthResponse {

        val user = authService.login(
            email = request.email,
            rawPassword = request.password
        )

        val token = jwtService.generateToken(
            user.email,
            user.role.name
        )

        return AuthResponse(token)
    }
}
