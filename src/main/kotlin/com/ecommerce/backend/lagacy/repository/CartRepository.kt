package com.ecommerce.backend.lagacy.repository

import com.ecommerce.backend.lagacy.entity.Cart
import org.springframework.data.mongodb.repository.MongoRepository

interface CartRepository : MongoRepository<Cart, String> {
    fun findByUserEmail(userEmail: String): Cart?
}