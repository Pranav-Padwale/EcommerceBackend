package com.ecommerce.backend.service

import com.ecommerce.backend.model.User
import com.ecommerce.backend.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(name: String, email: String, rawPassword: String) {

        if (userRepository.findByEmail(email) != null) {
            throw IllegalArgumentException("Email already registered")
        }

        val user = User(
            name = name,
            email = email,
            password = passwordEncoder.encode(rawPassword),
            role = "USER"
        )

        userRepository.save(user)
    }

    fun login(email: String, rawPassword: String): User {

        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("Invalid credentials")

        if (!passwordEncoder.matches(rawPassword, user.password)) {
            throw IllegalArgumentException("Invalid credentials")
        }

        return user
    }
}
