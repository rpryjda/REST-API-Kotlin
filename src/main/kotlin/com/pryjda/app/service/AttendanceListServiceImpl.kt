package com.pryjda.app.service

import com.pryjda.app.exception.WrongLectureIdException
import com.pryjda.app.exception.WrongUserIdException
import com.pryjda.app.repository.LectureRepository
import com.pryjda.app.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AttendanceListServiceImpl @Autowired constructor(val userRepository: UserRepository,
                                                       val lectureRepository: LectureRepository) : AttendanceListService {
    @Transactional
    override fun createAttendanceOnLectureByUserId(idLecture: Long, idUser: Long): Boolean = lectureRepository
            .findById(idLecture)
            .map { lecture ->
                userRepository
                        .findById(idUser)
                        .map { lecture.users.add(it) }
                        .orElseThrow { WrongUserIdException("Wrong ID number for user") }
            }
            .orElseThrow { WrongLectureIdException("Wrong id number for lecture") }
}