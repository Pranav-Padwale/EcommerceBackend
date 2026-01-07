package com.ecommerce.backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "cart")
data class Cart(
    @Id
    val id: String? = null,
    val userEmail: String,
    val items: List<CartItem> = emptyList()
)

data class CartItem(
    val productId: String,
    val quantity: Int,
)