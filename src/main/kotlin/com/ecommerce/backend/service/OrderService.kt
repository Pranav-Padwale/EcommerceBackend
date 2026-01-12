package com.ecommerce.backend.service

import com.ecommerce.backend.model.Order
import com.ecommerce.backend.model.OrderStatus
import com.ecommerce.backend.repository.CartRepository
import com.ecommerce.backend.repository.OrderRepository
import com.ecommerce.backend.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) {

    // USER places order
    fun placeOrder(userEmail: String): Order {

        //  Place order FIRST
        val order = Order(userEmail = userEmail)
        val savedOrder = orderRepository.save(order)

        //  THEN reduce stock
        reduceProductStockForUser(userEmail)

        return savedOrder
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

    // ===============================
    //  STOCK REDUCTION (AFTER ORDER)
    // ===============================
    private fun reduceProductStockForUser(userEmail: String) {

        val cart = cartRepository.findByUserEmail(userEmail)
            ?: throw IllegalStateException("Cart is empty")

        cart.items.forEach { cartItem ->

            val product = productRepository.findById(cartItem.productId)
                .orElseThrow { IllegalStateException("Product not found") }

            if (product.stock < cartItem.quantity) {
                throw IllegalStateException(
                    "Insufficient stock for product: ${product.name}"
                )
            }

            val updatedProduct = product.copy(
                stock = product.stock - cartItem.quantity
            )

            productRepository.save(updatedProduct)
        }

        // Clear cart after successful order
        cartRepository.delete(cart)
    }
}
