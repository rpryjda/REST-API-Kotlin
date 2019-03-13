package com.pryjda.app.entity

import javax.persistence.*

@Entity
data class UserProfile(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var name: String? = null,
        var surname: String? = null,
        var academicYear: String? = null,
        var courseOfStudy: String? = null) {

    @OneToOne(mappedBy = "userProfile")
    @Transient
    var user: User? = null
}