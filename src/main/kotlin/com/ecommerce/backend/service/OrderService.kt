package com.ecommerce.backend.service

import com.ecommerce.backend.model.Order
import com.ecommerce.backend.model.OrderStatus
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

        println("placeOrder() called for user: $userEmail")

        val cart = cartRepository.findByUserEmail(userEmail)
            ?: throw RuntimeException("Cart not found")

        println("Cart found with ${cart.items.size} items")

        if (cart.items.isEmpty()) {
            throw RuntimeException("Cart is empty")
        }

        cart.items.forEach { item ->

            println("Checking product: ${item.productId}")

            val product = productRepository.findById(item.productId)
                .orElseThrow {
                    println("Product Not Found for ID: ${item.productId}")
                    RuntimeException("Product Not Found")
                }

            println("Product found: ${product.name}, stock=${product.stock}")


            if (product.stock < item.quantity) {
                throw RuntimeException("Insufficient stock for product ${product.name}")

            }

            val updateProduct = product.copy(
                stock = product.stock - item.quantity,
            )

           productRepository.save(updateProduct)

           println("Stock updated for product ${product.name}, new stock: ${updateProduct.stock}")

        }

        val order = Order(
            userEmail = userEmail,
            items = cart.items
        )

        println(" Saving order for user: $userEmail")

        val savedOrder = orderRepository.save(order)

        println("Order saved with ID: ${savedOrder.id}")

        cartRepository.save(cart.copy(items = emptyList()))
        println("Cart cleard for user: $userEmail")

        return savedOrder
    }

    // User : view own orders
    fun getOrdersForUser(userEmail: String): List<Order> {

        println("Fetching order for user: $userEmail")

        return orderRepository.findByUserEmail(userEmail)
    }

    //Admin : view all orders
    fun getAllOrders(): List<Order> {
        println("Admin fetching orders")
        return orderRepository.findAll()
    }



    //Admin Update order status
    fun updateOrderStatus(orderId: String, status: OrderStatus): Order {

        println("Updating order status for , orderId: $orderId, newStatus=$status")
        val order = orderRepository.findById(orderId)
            .orElseThrow{ RuntimeException("Order not found") }

        val updatedOrder = order.copy(status = status)

        val savedOrder = orderRepository.save(updatedOrder)

        println("Order status update , orderId=${savedOrder.id}, status=${savedOrder.status}")

        return savedOrder
    }
}
