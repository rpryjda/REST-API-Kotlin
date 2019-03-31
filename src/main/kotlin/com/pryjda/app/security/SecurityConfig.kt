package com.pryjda.app.security

import com.pryjda.app.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
class SecurityConfig(@Autowired val userRepository: UserRepository) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth
                ?.userDetailsService(UserDetailsServiceImpl(userRepository))
    }

    override fun configure(http: HttpSecurity?) {
        http
                ?.httpBasic()
                ?.and()
                ?.authorizeRequests()
                ?.anyRequest()
                ?.fullyAuthenticated()
                ?.and()
                ?.csrf()?.disable()
                ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}