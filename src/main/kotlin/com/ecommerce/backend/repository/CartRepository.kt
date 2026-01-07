package com.ecommerce.backend.repository

import com.ecommerce.backend.model.Cart
import org.springframework.data.mongodb.repository.MongoRepository

interface CartRepository : MongoRepository<Cart, String> {
    fun findByUserEmail(userEmail: String): Cart?
}