package com.ecommerce.backend.lagacy.controller

import com.ecommerce.backend.lagacy.entity.Order
import com.ecommerce.backend.lagacy.service.OrderService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
class AdminOrderController(
    private val orderService: OrderService
) {

    // ADMIN: view all orders
    @GetMapping
    fun getAllOrders(): List<Order> {
        return orderService.getAllOrders()
    }

    // ADMIN: ship order
    @PutMapping("/{orderId}/ship")
    fun shipOrder(@PathVariable orderId: String): Order {
        return orderService.shipOrder(orderId)
    }

    // ADMIN: deliver order
    @PutMapping("/{orderId}/deliver")
    fun deliverOrder(@PathVariable orderId: String): Order {
        return orderService.deliverOrder(orderId)
    }
}
