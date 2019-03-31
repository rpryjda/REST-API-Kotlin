package com.pryjda.app.security

import com.pryjda.app.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserDetailsServiceImpl(val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails = userRepository
            .findUserByEmail(username)
            .map {
                val authorities = mutableListOf<GrantedAuthority>()
                it.roles.forEach { userRole -> authorities.add(SimpleGrantedAuthority(userRole.name)) }
                User
                        .withUsername(it.email)
                        .password("{noop}${it.password}")
                        .authorities(authorities)
                        .build()
            }
            .orElseThrow { UsernameNotFoundException("Username wasn't found") }
}