package com.ecommerce.backend.lagacy.config

import com.ecommerce.backend.lagacy.entity.User
import com.ecommerce.backend.lagacy.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AdminSeeder {

    /**
     * ✅ Seeds a default ADMIN user at application startup
     * FIX:
     * - Explicitly sets active = true
     */
    @Bean
    fun seedAdminUser(
        userRepository: UserRepository,
        passwordEncoder: PasswordEncoder
    ): CommandLineRunner {
        return CommandLineRunner {

            val adminEmail = "admin@example.com"

            if (!userRepository.existsByEmail(adminEmail)) {

                val adminUser = User(
                    name = "Admin",
                    email = adminEmail,
                    password = passwordEncoder.encode("admin123"),
                    role = "ADMIN",
                    active = true // ✅ FIX: admin is active by default
                )

                userRepository.save(adminUser)
            }
        }
    }
}
