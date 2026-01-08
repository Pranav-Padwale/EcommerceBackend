package com.ecommerce.backend.service

import com.ecommerce.backend.model.Order
import com.ecommerce.backend.repository.CartRepository
import com.ecommerce.backend.repository.OrderRepository
import com.ecommerce.backend.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository

) {

    fun placeOrder(userEmail: String): Order {

        val cart = cartRepository.findByUserEmail(userEmail)
            ?: throw RuntimeException("Cart not found")

        if (cart.items.isEmpty()) {
            throw RuntimeException("Cart is empty")
        }

        cart.items.forEach { item ->
            val product = productRepository.findById(item.productId)
                .orElseThrow { throw RuntimeException("Product not found") }

            if (product.stock < item.quantity) {
                throw RuntimeException("Insufficient stock for product ${product.name}")

            }

            val updateProduct = product.copy(
                stock = product.stock - item.quantity,
            )

            productRepository.save(updateProduct)


        }

        val order = Order(
            userEmail = userEmail,
            items = cart.items
        )

        val savedOrder = orderRepository.save(order)

        cartRepository.save(cart.copy(items = emptyList()))

        return savedOrder
    }

    fun getOrders(userEmail: String): List<Order> {
        return orderRepository.findByUserEmail(userEmail)
    }
}
