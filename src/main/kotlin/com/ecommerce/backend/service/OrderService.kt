package com.ecommerce.backend.service

import com.ecommerce.backend.model.Order
import com.ecommerce.backend.repository.CartRepository
import com.ecommerce.backend.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
) {

    fun placeOrder(userEmail: String): Order {

        val cart = cartRepository.findByUserEmail(userEmail)
            ?: throw RuntimeException("Cart not found")

        if (cart.items.isEmpty()) {
            throw RuntimeException("Cart is empty")
        }

        val order = Order(
            userEmail = userEmail,
            items = cart.items
        )

        cartRepository.save(cart.copy(items = emptyList()))

        return orderRepository.save(order)
    }

    fun getOrders(userEmail: String): List<Order> {
        return orderRepository.findByUserEmail(userEmail)
    }
}
