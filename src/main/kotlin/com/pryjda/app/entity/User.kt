package com.pryjda.app.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
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
    @JsonIgnore
    var lectures: MutableSet<Lecture> = mutableSetOf()

    @ManyToMany(cascade = arrayOf(CascadeType.ALL))
    @JoinTable(
            name = "user_role",
            joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "id")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "role_id", referencedColumnName = "id")))
    @JsonIgnore
    var roles: MutableSet<Role> = mutableSetOf()

    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "user_profile_id", unique = true)
    @JsonIgnoreProperties("name", "surname", "academicYear", "courseOfStudy", "user")
    var userProfile: UserProfile? = null
}

