package com.ecommerce.backend.lagacy.controller

import com.ecommerce.backend.lagacy.entity.Order
import com.ecommerce.backend.lagacy.service.OrderService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService
) {

    private fun currentUserEmail(): String {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("User is not authenticated")

        return authentication.name
    }

    // USER places order
    @PostMapping
    fun placeOrder(): Order {
        return orderService.placeOrder(currentUserEmail())
    }

    // USER views own orders
    @GetMapping
    fun getMyOrders(): List<Order> {
        return orderService.getOrdersForUser(currentUserEmail())
    }
}
