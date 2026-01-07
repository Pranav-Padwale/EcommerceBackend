package com.ecommerce.backend.model

import ch.qos.logback.core.status.Status
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

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