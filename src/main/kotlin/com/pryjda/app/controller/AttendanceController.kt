package com.pryjda.app.controller

import com.pryjda.app.service.AttendanceListService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AttendanceController(@Autowired val attendanceListService: AttendanceListService) {

    @PostMapping("/registry/lectures/{lecture_id}/users/{user_id}")
    fun registerUserToLecture(@PathVariable(value = "lecture_id") lectureID: Long,
                              @PathVariable(value = "user_id") userID: Long) = attendanceListService
            .createAttendanceOnLectureByUserId(lectureID, userID)
}