package com.ecommerce.backend.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import java.util.Base64
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}")
    private val jwtSecret: String
) {

    private val key: SecretKey =
        Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret))

    fun generateToken(email: String, role: String): String {

        // ✅ Normalize role to Spring Security standard
        val normalizedRole =
            if (role.startsWith("ROLE_")) role else "ROLE_$role"

        return Jwts.builder()
            .setSubject(email)
            .claim("role", normalizedRole)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 86400000)) // 24h
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
}
