package com.ecommerce.backend.lagacy.repository

import com.ecommerce.backend.lagacy.entity.Product
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository : MongoRepository<Product, String>
