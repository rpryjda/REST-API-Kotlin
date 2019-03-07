package com.pryjda.app.service

import com.pryjda.app.entity.User

interface UserService {

    fun readUsers(): List<User>
    fun readSingleUser(id: Long): User
    fun createUser(user: User): User
    fun updateUser(id: Long, user: User): User
    fun deleteUser(id: Long): Boolean
}