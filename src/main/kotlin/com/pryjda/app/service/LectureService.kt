package com.pryjda.app.service

import com.pryjda.app.model.request.LectureRequestDTO
import com.pryjda.app.model.response.LectureResponseDTO

interface LectureService {

    fun readLectures(): List<LectureResponseDTO>
    fun readSingleLecture(id: Long): LectureResponseDTO
    fun createLecture(lecture: LectureRequestDTO): LectureResponseDTO
    fun updateLectureById(id: Long, lecture: LectureRequestDTO): LectureResponseDTO
    fun deleteLectureById(id: Long)
}