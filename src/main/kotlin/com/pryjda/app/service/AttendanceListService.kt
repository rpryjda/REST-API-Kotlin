package com.pryjda.app.service

interface AttendanceListService {

    fun createAttendanceOnLectureByUserId(idLecture: Long, idUser: Long): Boolean
}