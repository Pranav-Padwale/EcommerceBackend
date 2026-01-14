package com.ecommerce.backend.lagacy.repository

import com.ecommerce.backend.lagacy.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {

    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean
}
