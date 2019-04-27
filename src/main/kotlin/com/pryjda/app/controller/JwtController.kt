package com.pryjda.app.controller

import com.pryjda.app.filter.JwtTokenProvider
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JwtController(val jwtTokenProvider: JwtTokenProvider) {

    @GetMapping("/token")
    fun getToken(@AuthenticationPrincipal userDetails: UserDetails): String =
            jwtTokenProvider.createToken(userDetails.username,
                    userDetails.authorities.map { it.authority })
}