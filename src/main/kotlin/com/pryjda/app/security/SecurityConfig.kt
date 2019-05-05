package com.pryjda.app.security

import com.pryjda.app.filter.SignFilter
import com.pryjda.app.filter.jwt.JwtConfigurer
import com.pryjda.app.filter.jwt.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.header.HeaderWriterFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(@Qualifier("userDetails") @Autowired val userDetailsService: UserDetailsService,
                     val jwtTokenProvider: JwtTokenProvider) : WebSecurityConfigurerAdapter() {

//    override fun configure(auth: AuthenticationManagerBuilder?) {
//        auth
//                ?.userDetailsService(userDetailsService)
//                ?.passwordEncoder(bCryptPasswordEncoder())
//    }

    override fun configure(http: HttpSecurity?) {
        http
                ?.httpBasic()
                ?.and()
                ?.csrf()?.disable()
                ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ?.and()
                ?.authorizeRequests()
                ?.anyRequest()
                ?.authenticated()
                ?.and()
                ?.apply(JwtConfigurer(jwtTokenProvider))
                ?.and()
                ?.addFilterAfter(SignFilter(), HeaderWriterFilter::class.java)
    }

    override fun configure(web: WebSecurity?) {
        web
                ?.ignoring()
                ?.antMatchers("/auth-body")
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

//    @Bean
//    fun signFilter(): FilterRegistrationBean<SignFilter> {
//        val registration = FilterRegistrationBean<SignFilter>()
//        registration.filter = SignFilter()
//        registration.addUrlPatterns("/lectures")
//        return registration
//    }
}