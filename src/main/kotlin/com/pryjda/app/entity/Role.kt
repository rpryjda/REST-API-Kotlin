package com.pryjda.app.entity

import javax.persistence.*

@Entity
data class Role(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var name: String? = null) {

    @ManyToMany(mappedBy = "roles")
    var users: MutableSet<User> = mutableSetOf()
}