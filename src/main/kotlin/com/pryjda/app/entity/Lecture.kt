package com.pryjda.app.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Lecture(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var title: String? = null,
        var description: String? = null,
        var lecturer: String? = null,
        @Column(columnDefinition = "TIMESTAMP")
        var date: LocalDateTime? = null) {

    @ManyToMany(cascade = arrayOf(CascadeType.MERGE, CascadeType.PERSIST))
    @JoinTable(
            name = "user_lecture",
            joinColumns = arrayOf(JoinColumn(name = "lecture_id", referencedColumnName = "id")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "id")))
    var users: MutableSet<User> = mutableSetOf()
}
