package com.ecommerce.backend.repository

import com.ecommerce.backend.model.Product
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository : MongoRepository<Product, String>
