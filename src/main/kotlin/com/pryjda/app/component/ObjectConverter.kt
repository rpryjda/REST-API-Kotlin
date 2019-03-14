package com.pryjda.app.component

import com.pryjda.app.entity.Lecture
import com.pryjda.app.entity.User
import com.pryjda.app.entity.UserProfile
import com.pryjda.app.model.request.LectureRequestDTO
import com.pryjda.app.model.request.UserRequestDTO
import com.pryjda.app.model.response.LectureResponseDTO
import com.pryjda.app.model.response.UserResponseDTO
import org.springframework.stereotype.Component

@Component
class ObjectConverter {

    fun mapLectureRequestDTOtoLecture(lectureRequestDTO: LectureRequestDTO): Lecture =
            Lecture(null,
                    lectureRequestDTO.title,
                    lectureRequestDTO.description,
                    lectureRequestDTO.lecturer,
                    lectureRequestDTO.date)

    fun mapLectureToLectureResponseDTO(lecture: Lecture): LectureResponseDTO =
            LectureResponseDTO(lecture.id,
                    lecture.title,
                    lecture.description,
                    lecture.lecturer,
                    lecture.date)

    fun mapUserRequestDTOtoUser(userRequestDTO: UserRequestDTO): User =
            User(null,
                    userRequestDTO.email,
                    userRequestDTO.password,
                    userRequestDTO.indexNumber)

    fun mapUserRequestDTOtoUserProfile(userRequestDTO: UserRequestDTO): UserProfile =
            UserProfile(null,
                    userRequestDTO.name,
                    userRequestDTO.surname,
                    userRequestDTO.academicYear,
                    userRequestDTO.courseOfStudy)

    fun mapUserAndUserProfileToUserResponseDTO(user: User, userProfile: UserProfile?): UserResponseDTO =
            UserResponseDTO(user.id,
                    userProfile?.name,
                    userProfile?.surname,
                    user.email,
                    user.indexNumber,
                    userProfile?.academicYear,
                    userProfile?.courseOfStudy)
}