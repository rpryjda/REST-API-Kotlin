package com.pryjda.app.entity

import javax.persistence.*

@Entity
data class Lecture(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var title: String? = null,
        var describtion: String? = null,
        var lecturers: String? = null) {

    @ManyToMany(cascade = arrayOf(CascadeType.ALL))
    @JoinTable(
            name = "user_lecture",
            joinColumns = arrayOf(JoinColumn(name = "lecture_id", referencedColumnName = "id")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "id")))
    var users: MutableSet<User> = mutableSetOf()
}
