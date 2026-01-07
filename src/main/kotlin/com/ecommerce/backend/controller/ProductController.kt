package com.ecommerce.backend.controller

import com.ecommerce.backend.model.Product
import com.ecommerce.backend.repository.ProductRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productRepository: ProductRepository
) {

    // USER + ADMIN
    @GetMapping
    fun getAllProducts(): List<Product> =
        productRepository.findAll()

    // ADMIN only
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun addProduct(@RequestBody product: Product): Product =
        productRepository.save(product)

    // ADMIN only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteProduct(@PathVariable id: String) {
        productRepository.deleteById(id)
    }
}
