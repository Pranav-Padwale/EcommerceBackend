package com.ecommerce.backend.service

import com.ecommerce.backend.model.Role
import com.ecommerce.backend.model.User
import com.ecommerce.backend.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(
        name: String,
        email: String,
        rawPassword: String   // ✅ NON-NULL
    ) {
        if (userRepository.existsByEmail(email)) {
            throw RuntimeException("Email already registered")
        }

        val encodedPassword = requireNotNull(
            passwordEncoder.encode(rawPassword)
        ) { "Password encoding failed" }



        val user = User(
            name = name,
            email = email,
            password = encodedPassword,
            role = Role.USER
        )

        userRepository.save(user)
    }

    fun login(
        email: String,
        rawPassword: String   // ✅ NON-NULL
    ): User {

        val user = userRepository.findByEmail(email)
            ?: throw RuntimeException("Invalid credentials")

        if (!passwordEncoder.matches(rawPassword, user.password)) {
            throw RuntimeException("Invalid credentials")
        }

        return user
    }
}
