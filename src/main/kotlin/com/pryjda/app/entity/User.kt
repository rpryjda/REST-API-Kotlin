package com.pryjda.app.entity

import javax.persistence.*

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        @Column(unique = true)
        var email: String? = null,
        var password: String? = null,
        @Column(unique = true)
        var indexNumber: Int? = null,
        var enabled: Boolean? = null)

