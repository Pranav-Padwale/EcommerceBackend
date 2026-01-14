package com.ecommerce.backend.lagacy.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(

    @Id
    val id: String? = null,

    val name: String,

    val email: String,

    val password: String,

    val role: String = "USER",

    // 🔴 IMPORTANT:
    // active is now controlled by business logic
    val active: Boolean
)
