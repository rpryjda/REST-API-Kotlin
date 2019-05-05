package com.pryjda.app.service

import com.pryjda.app.model.request.AuthRequestDTO
import com.pryjda.app.model.response.AuthResponseDTO
import org.springframework.security.core.userdetails.UserDetails

interface AuthenticationService {

    fun authenticateByBasicAuth(userDetails: UserDetails, authWay: String?): AuthResponseDTO
    fun authenticateByRequestBody(authRequestDTO: AuthRequestDTO, authWay: String?): AuthResponseDTO
    fun isAuthenticated(login: String, id: Long): Boolean
}