package com.pryjda.app.filter

import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class EncryptFilter : GenericFilterBean() {


    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

    }
}