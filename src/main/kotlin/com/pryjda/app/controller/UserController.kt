package com.pryjda.app.controller

import com.pryjda.app.entity.User
import com.pryjda.app.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(@Autowired val userService: UserService) {

    @GetMapping
    fun getUsers(): List<User> = userService.readUsers()

    @GetMapping("/{id}")
    fun getSingleUser(@PathVariable id: Long): User = userService.readSingleUser(id)

    @PostMapping
    fun createUser(@RequestBody user: User): User = userService.createUser(user)

    @PutMapping("/{id}")
    fun update(@RequestBody user: User, @PathVariable id: Long): User = userService.updateUser(id, user)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): Boolean = userService.deleteUser(id)
}