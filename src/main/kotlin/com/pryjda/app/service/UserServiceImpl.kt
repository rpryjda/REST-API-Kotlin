package com.pryjda.app.service

import com.pryjda.app.entity.User
import com.pryjda.app.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(@Autowired val userRepository: UserRepository) : UserService {

    override fun readUsers(): List<User> = userRepository.findAll()

    override fun readSingleUser(id: Long): User = userRepository.getOne(id)

    override fun createUser(user: User): User = userRepository.save(user)

    override fun updateUser(id: Long, user: User): User {
        TODO("not implemented")
    }

    override fun deleteUser(id: Long): Boolean {
        TODO("not implemented")
    }
}