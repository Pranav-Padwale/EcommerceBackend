package com.ecommerce.backend.lagacy.repository

import com.ecommerce.backend.lagacy.entity.Order
import org.springframework.data.mongodb.repository.MongoRepository

interface OrderRepository : MongoRepository<Order, String> {
    fun findByUserEmail(userEmail: String): List<Order>
}
