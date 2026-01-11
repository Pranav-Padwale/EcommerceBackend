package com.ecommerce.backend.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Base64
import javax.crypto.SecretKey

class JwtAuthenticationFilter(
    jwtSecret: String
) : OncePerRequestFilter() {

    private val key: SecretKey =
        Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret))

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader = request.getHeader("Authorization")

        // ✅ If token not present, continue (important for login/register)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(7)

        try {
            val claims: Claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body

            val email = claims.subject
            val role = claims["role"] as String

            // ✅ Double safety
            val authority =
                if (role.startsWith("ROLE_")) role else "ROLE_$role"

            val authentication = UsernamePasswordAuthenticationToken(
                email,
                null,
                listOf(SimpleGrantedAuthority(authority))
            )

            SecurityContextHolder.getContext().authentication = authentication

        } catch (ex: Exception) {
            SecurityContextHolder.clearContext()
        }

        filterChain.doFilter(request, response)
    }
}
