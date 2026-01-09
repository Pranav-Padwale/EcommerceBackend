package com.ecommerce.backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "orders")
data class Order(
    @Id
    val id: String? = null,
    val userEmail: String,
    val items: List<CartItem>,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val status: OrderStatus = OrderStatus.PLACED
)

enum class OrderStatus {
    PLACED,
    SHIPPED,
    DELIVERED
}
