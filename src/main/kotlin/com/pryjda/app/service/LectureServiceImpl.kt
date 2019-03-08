package com.pryjda.app.service

import com.pryjda.app.entity.Lecture
import com.pryjda.app.repository.LectureRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LectureServiceImpl(@Autowired val lectureRepository: LectureRepository) : LectureService {

    override fun readLectures(): List<Lecture> = lectureRepository.findAll()

    override fun readSingleLecture(id: Long): Lecture = lectureRepository.getOne(id)

    override fun createLecture(lecture: Lecture): Lecture = lectureRepository.save(lecture)

    override fun updateLectureById(id: Long, lecture: Lecture): Lecture {
        TODO("not implemented")
    }

    override fun deleteLectureById(id: Long): Boolean {
        TODO("not implemented")
    }
}