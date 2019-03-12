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
        var enabled: Boolean? = null) {

    @ManyToMany(mappedBy = "users")
    var lectures: MutableSet<Lecture> = mutableSetOf()

    @ManyToMany(cascade = arrayOf(CascadeType.ALL))
    @JoinTable(
            name = "user_role",
            joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "id")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "role_id", referencedColumnName = "id")))
    var roles: MutableSet<Role> = mutableSetOf()

    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "user_profile_id", unique = true)
    var userProfile: UserProfile? = null
}

