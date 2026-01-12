package com.ecommerce.backend.config

import com.ecommerce.backend.model.User
import com.ecommerce.backend.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class AdminSeeder {

    @Bean
    fun createAdminUser(
        userRepository: UserRepository,
        passwordEncoder: PasswordEncoder
    ) = CommandLineRunner {

        val adminEmail = "admin@test.com"

        val existingAdmin = userRepository.findByEmail(adminEmail)

        if (existingAdmin == null) {

            val admin = User(
                name = "Admin User",
                email = adminEmail,
                password = passwordEncoder.encode("admin123"),
                role = "ADMIN"
            )

            userRepository.save(admin)

            println("✅ Admin user CREATED")
            println("📧 Email: admin@test.com")
            println("🔑 Password: admin123")

        } else {
            println("ℹ️ Admin user already exists")
        }
    }
}
