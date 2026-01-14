package com.ecommerce.backend.lagacy.security

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

    // =====================================================
    // ✅ IMPORTANT CHANGE:
    // Tell Spring Security to COMPLETELY SKIP this filter
    // for register, login, and query APIs
    // =====================================================
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI

        return path.startsWith("/api/auth/") ||   // register & login
                path.startsWith("/api/query/") ||  // CQRS query APIs
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader = request.getHeader("Authorization")

        // If no token → continue without authentication
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

            val authority = SimpleGrantedAuthority("ROLE_$role")

            val authentication = UsernamePasswordAuthenticationToken(
                email,
                null,
                listOf(authority)
            )

            SecurityContextHolder.getContext().authentication = authentication

        } catch (ex: Exception) {
            SecurityContextHolder.clearContext()
        }

        filterChain.doFilter(request, response)
    }
}
