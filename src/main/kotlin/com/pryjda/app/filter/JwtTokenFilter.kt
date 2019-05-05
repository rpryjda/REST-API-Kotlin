package com.pryjda.app.filter

import com.pryjda.app.filter.jwt.JwtTokenProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider) : GenericFilterBean() {


    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val token = jwtTokenProvider.resolveToken(request as HttpServletRequest)
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val auth: UsernamePasswordAuthenticationToken = token.let { jwtTokenProvider.getAuthentication(token) }
            SecurityContextHolder.getContext().authentication = auth
            println("jestem tu: " + SecurityContextHolder.getContext().authentication.principal)
        }
        chain.doFilter(request, response)
    }

//    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
//        val token = jwtTokenProvider.resolveToken(request as HttpServletRequest)
//        if (token != null && jwtTokenProvider.validateTokenRSA(token)) {
//            val auth: UsernamePasswordAuthenticationToken = token.let { jwtTokenProvider.getAuthenticationRSA(token) }
//            SecurityContextHolder.getContext().authentication = auth
//            println("jestem tu: " + SecurityContextHolder.getContext().authentication.principal)
//        }
//        chain.doFilter(request, response)
//    }
}