package com.pryjda.app.security

import com.pryjda.app.filter.JWTAuthenticationFilter
import com.pryjda.app.filter.JWTAuthorizationFilter
import com.pryjda.app.filter.JwtConfigurer
import com.pryjda.app.filter.JwtTokenProvider
import com.pryjda.app.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(@Autowired val userRepository: UserRepository,
                     val jwtTokenProvider: JwtTokenProvider) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth
                ?.userDetailsService(UserDetailsServiceImpl(userRepository))
                ?.passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity?) {
        http
                ?.httpBasic()?.disable()
//                ?.and()
                ?.authorizeRequests()
                ?.anyRequest()
                ?.fullyAuthenticated()
                ?.and()
                ?.apply(JwtConfigurer(jwtTokenProvider))
                ?.and()
//                ?.addFilter(JWTAuthenticationFilter(authenticationManager()))
//                ?.addFilter(JWTAuthorizationFilter(authenticationManager()))
                ?.csrf()?.disable()
                ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}