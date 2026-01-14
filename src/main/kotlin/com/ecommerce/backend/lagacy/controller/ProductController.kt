package com.ecommerce.backend.lagacy.controller

import com.ecommerce.backend.lagacy.entity.Product
import com.ecommerce.backend.lagacy.repository.ProductRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val productRepository: ProductRepository
) {

    @GetMapping
    fun getAllProducts() = productRepository.findAll()

    @PostMapping
    fun addProduct(@RequestBody product: Product) =
        productRepository.save(product)

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: String) =
        productRepository.deleteById(id)
}
