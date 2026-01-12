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
        return Jwts.builder()
            .setSubject(email)
            .claim("role", role) // "ADMIN" / "USER"
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 86400000))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }
}
