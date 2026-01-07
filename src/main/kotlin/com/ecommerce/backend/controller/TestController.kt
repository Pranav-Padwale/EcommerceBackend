package com.ecommerce.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TestController {

    @GetMapping("/secure")
    fun secureApi(): String {
        return "JWT is working! 🔐"
    }
}
