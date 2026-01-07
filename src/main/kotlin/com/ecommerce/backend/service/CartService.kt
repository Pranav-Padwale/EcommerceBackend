package com.ecommerce.backend.service

import com.ecommerce.backend.model.Cart
import com.ecommerce.backend.model.CartItem
import com.ecommerce.backend.repository.CartRepository
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository
) {

    fun getCart(userEmail: String): Cart {
        return cartRepository.findByUserEmail(userEmail)
            ?: cartRepository.save(Cart(userEmail = userEmail))
    }

    fun addToCart(userEmail: String, productId: String, quantity: Int): Cart {

        val cart = getCart(userEmail)

        val updatedItems = cart.items.toMutableList()
        val existingItem = updatedItems.find { it.productId == productId }

        if (existingItem != null) {
            updatedItems.remove(existingItem)
            updatedItems.add(
                existingItem.copy(quantity = existingItem.quantity + quantity)
            )
        } else {
            updatedItems.add(CartItem(productId, quantity))
        }

        return cartRepository.save(
            cart.copy(items = updatedItems)
        )
    }

    fun clearCart(userEmail: String) {
        val cart = getCart(userEmail)
        cartRepository.save(cart.copy(items = emptyList()))
    }
}
