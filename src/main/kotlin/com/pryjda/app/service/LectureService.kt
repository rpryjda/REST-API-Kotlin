package com.pryjda.app.service

import com.pryjda.app.entity.Lecture

interface LectureService {

    fun readLectures(): List<Lecture>
    fun readSingleLecture(id: Long): Lecture
    fun createLecture(lecture: Lecture): Lecture
    fun updateLectureById(id: Long, lecture: Lecture): Lecture
    fun deleteLectureById(id: Long)
}