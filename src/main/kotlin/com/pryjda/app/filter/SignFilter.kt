package com.pryjda.app.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse

class SignFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        response as HttpServletResponse
        response.addHeader("Custom-sign", "here")
        chain.doFilter(request, response)

        response.setHeader("Custom-sign", "here2")
        println("response headers: " + response.getHeader("Custom-sign"))
    }
}