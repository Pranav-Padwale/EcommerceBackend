package com.ecommerce.backend.lagacy.service

import com.ecommerce.backend.lagacy.entity.User
import com.ecommerce.backend.lagacy.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    /**
     * ✅ REGISTER
     * CHANGE:
     * - New users are INACTIVE by default
     */
    fun register(
        name: String,
        email: String,
        rawPassword: String
    ) {

        if (userRepository.existsByEmail(email)) {
            throw RuntimeException("User already exists")
        }

        val user = User(
            name = name,
            email = email,
            password = passwordEncoder.encode(rawPassword),
            role = "USER",
            active = false   // 🔴 FIX: inactive on registration
        )

        userRepository.save(user)
    }

    /**
     * ✅ LOGIN
     * CHANGE:
     * - On successful login, user becomes ACTIVE
     */
    fun login(email: String, rawPassword: String): User {

        val user = userRepository.findByEmail(email)
            ?: throw RuntimeException("Invalid credentials")

        if (!passwordEncoder.matches(rawPassword, user.password)) {
            throw RuntimeException("Invalid credentials")
        }

        // =================================================
        // Activate user on successful login
        // =================================================
        if (!user.active) {
            val activatedUser = user.copy(active = true)
            return userRepository.save(activatedUser)
        }

        return user
    }
}
