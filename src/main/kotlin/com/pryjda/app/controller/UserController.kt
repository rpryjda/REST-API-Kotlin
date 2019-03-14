package com.pryjda.app.controller

import com.pryjda.app.model.request.UserRequestDTO
import com.pryjda.app.model.response.UserResponseDTO
import com.pryjda.app.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(@Autowired val userService: UserService) {

    @GetMapping
    fun getUsers(): List<UserResponseDTO> = userService.readUsers()

    @GetMapping("/{id}")
    fun getSingleUser(@PathVariable id: Long): UserResponseDTO = userService.readSingleUser(id)

    @PostMapping
    fun createUser(@RequestBody user: UserRequestDTO): UserResponseDTO = userService.createUser(user)

    @PutMapping("/{id}")
    fun update(@RequestBody user: UserRequestDTO, @PathVariable id: Long): UserResponseDTO = userService.updateUser(id, user)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long) = userService.deleteUser(id)
}