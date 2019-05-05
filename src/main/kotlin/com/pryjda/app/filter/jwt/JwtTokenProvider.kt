package com.pryjda.app.filter.jwt

import com.pryjda.app.model.request.enum_field.EncryptAlgorithm
import com.pryjda.app.security.EXPIRATION_TIME
import com.pryjda.app.security.HEADER_AUTH
import com.pryjda.app.security.SECRET
import com.pryjda.app.security.TOKEN_PREFIX
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(@Qualifier("userDetails") val userDetailsService: UserDetailsService) {

    var secretKey: String = SECRET
    val validityInMiliseconds: Long = EXPIRATION_TIME
    var keyPair: KeyPair? = null
    var publicKey: PublicKey? = null
    var privateKey: PrivateKey? = null

    @PostConstruct
    fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        keyPair = keyPairGenerator.genKeyPair()
        publicKey = keyPair?.public
        privateKey = keyPair?.private

        println(Base64.getEncoder().encodeToString(publicKey?.encoded))
        println(Base64.getEncoder().encodeToString(privateKey?.encoded))
    }

    fun createToken(username: String?, roles: List<String>, encryptAlgorithm: EncryptAlgorithm): String {
        val claims: Claims = Jwts.claims().setSubject(username)
        claims.put("roles", roles)

        val currentDate: Date = Date()
        val validity: Date = Date(currentDate.time + validityInMiliseconds)

        when (encryptAlgorithm) {

            EncryptAlgorithm.HS256 -> return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(currentDate)
                    .setExpiration(validity)
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact()

            EncryptAlgorithm.RS256 -> return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(currentDate)
                    .setExpiration(validity)
                    .signWith(SignatureAlgorithm.RS256, privateKey)
                    .compact()
        }
    }

    fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(getUsername(token))!!
        return UsernamePasswordAuthenticationToken(userDetails.username, userDetails.password, userDetails.authorities)
    }

    fun getUsername(token: String) = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
            .subject

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken: String? = request.getHeader(HEADER_AUTH)
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX))
            return bearerToken.substring(7, bearerToken.length)
        return null
    }

    fun getAuthenticationRSA(token: String): UsernamePasswordAuthenticationToken {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(getUsernameRSA(token))!!
        return UsernamePasswordAuthenticationToken(userDetails.username, userDetails.password, userDetails.authorities)
    }

    fun getUsernameRSA(token: String) = Jwts.parser()
            .setSigningKey(publicKey)
            .parseClaimsJws(token)
            .body
            .subject

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
            throw RuntimeException("Expired or invalid JWT token")
        } catch (e: IllegalArgumentException) {
            throw RuntimeException("Expired or invalid JWT token")
        }
    }

    fun validateTokenRSA(token: String): Boolean {
        try {
            val claims: Jws<Claims> = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token)
            if (claims.body.expiration.before(Date()))
                return false
            return true
        } catch (e: JwtException) {
//            throw InvalidJwtAuthenticationException
            throw RuntimeException("Expired or invalid JWT token")
        } catch (e: IllegalArgumentException) {
            throw RuntimeException("Expired or invalid JWT token")
        }
    }
}