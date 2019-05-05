package com.pryjda.app.controller

import com.pryjda.app.model.request.UserRequestDTO
import com.pryjda.app.model.request.validation.order.CreateUserSequence
import com.pryjda.app.model.response.UserResponseDTO
import com.pryjda.app.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(@Autowired val userService: UserService) {

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostFilter("hasRole('ROLE_ADMIN') or" +
            "(hasRole('ROLE_USER') and authentication.name == filterObject.email)")
    fun getUsers(): List<UserResponseDTO> = userService.readUsers()

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostAuthorize("hasRole('ROLE_ADMIN') or" +
            "(hasRole('ROLE_USER') and authentication.name == returnObject.email)")
    fun getSingleUser(@PathVariable id: Long): UserResponseDTO = userService.readSingleUser(id)

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun createUser(@Validated(value = CreateUserSequence::class) @RequestBody user: UserRequestDTO): UserResponseDTO =
            userService.createUser(user)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or" +
            "(hasRole('ROLE_USER') and @authenticationServiceImpl.isAuthenticated(authentication.name, #id))")
    fun update(@RequestBody user: UserRequestDTO, @PathVariable id: Long): UserResponseDTO =
            userService.updateUser(id, user)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun deleteUser(@PathVariable id: Long) = userService.deleteUser(id)
}