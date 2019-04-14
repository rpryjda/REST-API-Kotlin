package com.pryjda.app.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.pryjda.app.entity.User
import com.pryjda.app.security.EXPIRATION_TIME
import com.pryjda.app.security.HEADER_STRING
import com.pryjda.app.security.SECRET
import com.pryjda.app.security.TOKEN_PREFIX
//import io.jsonwebtoken.Jwts
//import io.jsonwebtoken.SignatureAlgorithm
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.lang.RuntimeException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {


    override fun attemptAuthentication(request: HttpServletRequest?,
                                       response: HttpServletResponse?): Authentication {
        try {
            val creds = ObjectMapper().readValue(request?.inputStream, User::class.java)
            val authorities = mutableListOf<GrantedAuthority>()
            creds.roles.forEach { userRole ->
                authorities.add(SimpleGrantedAuthority(userRole.name))
            }
            return authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
                    creds.email,
                    creds.password
                    /*authorities*/))
        } catch (exe: IOException) {
            throw RuntimeException()
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest?,
                                          response: HttpServletResponse?,
                                          chain: FilterChain?,
                                          authResult: Authentication?) {

        val user: User = authResult?.principal as User

        val token: String = JWT.create()
                .withSubject(user.email)
                .withExpiresAt(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET))

        /*val token: String = Jwts.builder()
                .setSubject(user.email)
//                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact()*/
        response?.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
    }
}