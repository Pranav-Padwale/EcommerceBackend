package com.ecommerce.backend.controller


import com.ecommerce.backend.model.Order
import com.ecommerce.backend.service.OrderService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController (
    private val orderService: OrderService
){
    private fun currentUserEmail(): String =
        requireNotNull(SecurityContextHolder.getContext().authentication) {
            "User is not authenticated"
        }.name


    @PostMapping
    fun placeOrder(): Order{
        return orderService.placeOrder(currentUserEmail())
    }

    @GetMapping
    fun getMyOrders(): List<Order>{
        return orderService.getOrders(currentUserEmail())
    }
}