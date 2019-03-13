package com.pryjda.app.service

import com.pryjda.app.entity.Lecture
import com.pryjda.app.exception.WrongLectureIdException
import com.pryjda.app.repository.LectureRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LectureServiceImpl(@Autowired val lectureRepository: LectureRepository) : LectureService {

    override fun readLectures(): List<Lecture> = lectureRepository.findAll()

    override fun readSingleLecture(id: Long): Lecture = lectureRepository.getOne(id)

    override fun createLecture(lecture: Lecture): Lecture = lectureRepository.save(lecture)

    @Transactional
    override fun updateLectureById(id: Long, lecture: Lecture): Lecture = lectureRepository
            .findById(id)
            .map {
                if (lecture.describtion != null) it.describtion = lecture.describtion
                if (lecture.title != null) it.title = lecture.title
                if (lecture.lecturer != null) it.lecturer = lecture.lecturer
                it
            }
            .orElseThrow { WrongLectureIdException("Wrong id number for lecture") }


    override fun deleteLectureById(id: Long) = lectureRepository
            .findById(id)
            .map { lectureRepository.delete(it) }
            .orElseThrow { WrongLectureIdException("Wrong id number for lecture") }
}