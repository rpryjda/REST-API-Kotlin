package com.pryjda.app.filter

import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(private val userDetailsService: UserDetailsService) {

    var secretKey: String = "secret"
    val validityInMiliseconds: Long = 10 * 60 * 1000

    @PostConstruct
    fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(username: String, roles: List<String>): String {
        val claims: Claims = Jwts.claims().setSubject(username)
        claims.put("roles", roles)

        val currentDate: Date = Date()
        val validity: Date = Date(currentDate.time + validityInMiliseconds)

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String) = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
            .subject

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken: String = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7, bearerToken.length)
        return null
    }

    fun validateToken(token: String): Boolean {
        try {
            val claims: Jws<Claims> = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
            if (claims.body.expiration.before(Date()))
                return false
            return true
        } catch (e: JwtException) {
//            throw InvalidJwtAuthenticationException
            throw RuntimeException("Epired or invalid JWT token")
        } catch (e: IllegalArgumentException) {
            throw RuntimeException("Epired or invalid JWT token")
        }
    }
}