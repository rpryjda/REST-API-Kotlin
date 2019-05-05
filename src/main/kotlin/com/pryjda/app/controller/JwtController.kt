package com.pryjda.app.controller

import com.pryjda.app.service.AuthenticationService
import com.pryjda.app.model.request.AuthRequestDTO
import com.pryjda.app.model.response.AuthResponseDTO
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
class JwtController(val authService: AuthenticationService) {

    @GetMapping("/auth-header")
    fun authenticateByHeader(@AuthenticationPrincipal userDetails: UserDetails,
                             @RequestParam(value = "auth-way", required = false) authWay: String?): AuthResponseDTO =
            authService.authenticateByBasicAuth(userDetails, authWay)

    @PostMapping("/auth-body")
    fun authenticateByBody(@RequestBody authRequestDTO: AuthRequestDTO,
                           @RequestParam(value = "auth-way", required = false) authWay: String?): AuthResponseDTO =
            authService.authenticateByRequestBody(authRequestDTO, authWay)
}