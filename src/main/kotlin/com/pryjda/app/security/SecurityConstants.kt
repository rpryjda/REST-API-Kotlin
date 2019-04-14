package com.pryjda.app.security

val SECRET: String = "Secret_key_to_generate_JWTs"
val EXPIRATION_TIME: Long = 10 * 60 * 60 * 1000
val TOKEN_PREFIX: String = "Bearer "
val HEADER_STRING: String = "Authorization"
