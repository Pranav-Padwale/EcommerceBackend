package com.ecommerce.backend.lagacy.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "orders")
data class Order(
    @Id
    val id: String? = null,
    val userEmail: String,
    var status: OrderStatus = OrderStatus.PLACED
)
