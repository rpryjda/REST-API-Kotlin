package com.pryjda.app.service

import com.pryjda.app.filter.jwt.JwtTokenProvider
import com.pryjda.app.model.request.AuthRequestDTO
import com.pryjda.app.model.request.enum_field.EncryptAlgorithm
import com.pryjda.app.model.response.AuthResponseDTO
import com.pryjda.app.repository.UserRepository
import com.pryjda.app.security.TOKEN_PREFIX
import com.pryjda.app.security.WRONG_AUTH
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(private val authManager: AuthenticationManager,
                                @Qualifier("userDetails") private val userDetailsService: UserDetailsService,
                                val jwtTokenProvider: JwtTokenProvider,
                                val userRepository: UserRepository) : AuthenticationService {

    override fun authenticateByBasicAuth(userDetails: UserDetails, authWay: String?): AuthResponseDTO {

        val login = userDetails.username
        val roles = userDetails.authorities
                .map { it.authority }
        val token =
                when (authWay) {
                    "HS256" -> jwtTokenProvider.createToken(login, roles, EncryptAlgorithm.HS256)
                    "RS256" -> jwtTokenProvider.createToken(login, roles, EncryptAlgorithm.RS256)
                    else -> jwtTokenProvider.createToken(login, roles, EncryptAlgorithm.HS256)
                }
        return AuthResponseDTO(TOKEN_PREFIX + token)
    }

    override fun authenticateByRequestBody(authRequestDTO: AuthRequestDTO, authWay: String?): AuthResponseDTO {
        val authReq = UsernamePasswordAuthenticationToken(authRequestDTO.username, authRequestDTO.password)
        try {
            authManager.authenticate(authReq)
            val roles = userDetailsService
                    .loadUserByUsername(authRequestDTO.username)
                    .authorities
                    .map {
                        it.authority
                    }
            val token =
                    when (authWay) {
                        "HS256" -> jwtTokenProvider.createToken(authRequestDTO.username, roles, EncryptAlgorithm.HS256)
                        "RS256" -> jwtTokenProvider.createToken(authRequestDTO.username, roles, EncryptAlgorithm.RS256)
                        else -> jwtTokenProvider.createToken(authRequestDTO.username, roles, EncryptAlgorithm.HS256)
                    }
            return AuthResponseDTO(TOKEN_PREFIX + token)
        } catch (exe: AuthenticationException) {
            return AuthResponseDTO(WRONG_AUTH)
        }
    }

    override fun isAuthenticated(login: String, id: Long): Boolean {
        var isLogged: Boolean = false
        userRepository
                .findById(id)
                .ifPresent {
                    isLogged = it.email == login
                }
        return isLogged
    }
}