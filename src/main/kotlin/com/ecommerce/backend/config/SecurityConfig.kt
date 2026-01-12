package com.ecommerce.backend.config

import com.ecommerce.backend.security.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    @Value("\${jwt.secret}")
    private val jwtSecret: String
) {

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter =
        JwtAuthenticationFilter(jwtSecret)

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer =
        WebSecurityCustomizer { web ->
            web.ignoring().requestMatchers(
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
            )
        }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): SecurityFilterChain {

        http
            .cors { }
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {

                it.requestMatchers("/api/auth/**").permitAll()

                it.requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                it.requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                it.requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                it.requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

                it.requestMatchers("/admin/**").hasRole("ADMIN")
                it.requestMatchers("/cart/**", "/orders/**").authenticated()

                it.anyRequest().authenticated()
            }
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }
}
