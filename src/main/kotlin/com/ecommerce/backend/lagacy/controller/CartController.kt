package com.ecommerce.backend.lagacy.controller

import com.ecommerce.backend.lagacy.entity.Cart
import com.ecommerce.backend.lagacy.service.CartService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController(
    private val cartService: CartService
) {

    private fun currentUserEmail(): String =
        requireNotNull(SecurityContextHolder.getContext().authentication) {
            "User is not authenticated"
        }.name

    @GetMapping
    fun getCart(): Cart {
        return cartService.getCart(currentUserEmail())
    }

    @PostMapping("/add")
    fun addToCart(
        @RequestParam productId: String,
        @RequestParam quantity: Int
    ): Cart {
        return cartService.addToCart(currentUserEmail(), productId, quantity)
    }
}
