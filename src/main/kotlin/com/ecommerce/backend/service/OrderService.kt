package com.ecommerce.backend.service

import com.ecommerce.backend.model.Order
import com.ecommerce.backend.model.OrderStatus
import com.ecommerce.backend.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    // USER places order
    fun placeOrder(userEmail: String): Order {
        val order = Order(userEmail = userEmail)
        return orderRepository.save(order)
    }

    // USER views own orders
    fun getOrdersForUser(userEmail: String): List<Order> {
        return orderRepository.findByUserEmail(userEmail)
    }

    // ADMIN views all orders
    fun getAllOrders(): List<Order> {
        return orderRepository.findAll()
    }

    // ADMIN ships order
    fun shipOrder(orderId: String): Order {
        val order = getOrder(orderId)

        if (order.status != OrderStatus.PLACED) {
            throw IllegalStateException("Only PLACED orders can be shipped")
        }

        order.status = OrderStatus.SHIPPED
        return orderRepository.save(order)
    }

    // ADMIN delivers order
    fun deliverOrder(orderId: String): Order {
        val order = getOrder(orderId)

        if (order.status != OrderStatus.SHIPPED) {
            throw IllegalStateException("Only SHIPPED orders can be delivered")
        }

        order.status = OrderStatus.DELIVERED
        return orderRepository.save(order)
    }

    private fun getOrder(orderId: String): Order {
        return orderRepository.findById(orderId)
            .orElseThrow { RuntimeException("Order not found") }
    }
}
