package com.ecommerce.backend.controller

import com.ecommerce.backend.model.Order
import com.ecommerce.backend.model.OrderStatus
import com.ecommerce.backend.service.OrderService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/orders")
@PreAuthorize("hasAuthority('ADMIN')")
class AdminOrderController(
    private val orderService: OrderService
) {

    // ADMIN: View all orders
    @GetMapping
    fun getAllOrders(): List<Order> {
        return orderService.getAllOrders()
    }

    // ADMIN: Update order status
    @PutMapping("/{orderId}/status")
    fun updateOrderStatus(
        @PathVariable orderId: String,
        @RequestParam status: OrderStatus
    ): Order {
        return orderService.updateOrderStatus(orderId, status)
    }
}
