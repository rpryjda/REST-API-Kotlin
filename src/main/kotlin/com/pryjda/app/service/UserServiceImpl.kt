package com.pryjda.app.service

import com.pryjda.app.component.ObjectConverter
import com.pryjda.app.entity.User
import com.pryjda.app.entity.UserProfile
import com.pryjda.app.exception.WrongUserIdException
import com.pryjda.app.model.request.UserRequestDTO
import com.pryjda.app.model.response.UserResponseDTO
import com.pryjda.app.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl @Autowired constructor(val userRepository: UserRepository,
                                             val converter: ObjectConverter) : UserService {

    override fun readUsers(): List<UserResponseDTO> = userRepository
            .findAll()
            .map { converter.mapUserAndUserProfileToUserResponseDTO(it, it.userProfile) }

    override fun readSingleUser(id: Long): UserResponseDTO = userRepository
            .findById(id)
            .map { converter.mapUserAndUserProfileToUserResponseDTO(it, it.userProfile) }
            .orElseThrow { WrongUserIdException("Wrong ID number for user") }

    override fun createUser(userRequestDTO: UserRequestDTO): UserResponseDTO {
        var user: User = converter.mapUserRequestDTOtoUser(userRequestDTO)
        var userProfile: UserProfile = converter.mapUserRequestDTOtoUserProfile(userRequestDTO)
        user.userProfile = userProfile
        var persistedUser: User = userRepository.save(user)
        return converter.mapUserAndUserProfileToUserResponseDTO(persistedUser, persistedUser.userProfile)
    }

    @Transactional
    override fun updateUser(id: Long, userReqestDTO: UserRequestDTO): UserResponseDTO = userRepository
            .findById(id)
            .map {
                if (userReqestDTO.email != null) it.email = userReqestDTO.email
                if (userReqestDTO.indexNumber != null) it.indexNumber = userReqestDTO.indexNumber
                if (userReqestDTO.name != null) it.userProfile?.name = userReqestDTO.name
                if (userReqestDTO.surname != null) it.userProfile?.surname = userReqestDTO.surname
                if (userReqestDTO.academicYear != null) it.userProfile?.academicYear = userReqestDTO.academicYear
                if (userReqestDTO.courseOfStudy != null) it.userProfile?.courseOfStudy = userReqestDTO.courseOfStudy
                converter.mapUserAndUserProfileToUserResponseDTO(it, it.userProfile)
            }
            .orElseThrow { WrongUserIdException("Wrong ID number for user") }

    override fun deleteUser(id: Long) = userRepository
            .findById(id)
            .map { user ->
                user.lectures.forEach { lecture -> lecture.users.remove(user) }
                userRepository.deleteById(id)
            }
            .orElseThrow { WrongUserIdException("Wrong ID number for user") }
}