package com.ecommerce.backend.lagacy.controller

import com.ecommerce.backend.lagacy.dto.AuthResponse
import com.ecommerce.backend.lagacy.dto.LoginRequest
import com.ecommerce.backend.lagacy.dto.RegisterRequest
import com.ecommerce.backend.lagacy.security.JwtService
import com.ecommerce.backend.lagacy.service.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val jwtService: JwtService
) {

    /**
     * ✅ REGISTER USER
     * CHANGE: added `consumes = application/json`
     */
    @PostMapping(
        "/register",
        consumes = ["application/json"]
    )
    fun register(@Valid @RequestBody request: RegisterRequest) {
        authService.register(
            name = request.name,
            email = request.email,
            rawPassword = request.password
        )
    }

    /**
     * ✅ LOGIN USER
     */
    @PostMapping(
        "/login",
        consumes = ["application/json"]
    )
    fun login(@Valid @RequestBody request: LoginRequest): AuthResponse {

        val user = authService.login(
            email = request.email,
            rawPassword = request.password
        )

        val token = jwtService.generateToken(
            user.email,
            user.role
        )

        return AuthResponse(token)
    }
}
