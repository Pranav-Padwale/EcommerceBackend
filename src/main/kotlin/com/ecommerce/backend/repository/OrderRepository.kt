package com.ecommerce.backend.repository

import com.ecommerce.backend.model.Order
import org.springframework.data.mongodb.repository.MongoRepository

interface OrderRepository : MongoRepository<Order, String> {
    fun findByUserEmail(userEmail: String): List<Order>
}
