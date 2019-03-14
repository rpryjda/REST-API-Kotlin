package com.pryjda.app.service

import com.pryjda.app.model.request.UserRequestDTO
import com.pryjda.app.model.response.UserResponseDTO

interface UserService {

    fun readUsers(): List<UserResponseDTO>
    fun readSingleUser(id: Long): UserResponseDTO
    fun createUser(user: UserRequestDTO): UserResponseDTO
    fun updateUser(id: Long, user: UserRequestDTO): UserResponseDTO
    fun deleteUser(id: Long)
}