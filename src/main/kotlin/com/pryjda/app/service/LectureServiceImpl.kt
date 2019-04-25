package com.pryjda.app.service

import com.pryjda.app.component.ObjectConverter
import com.pryjda.app.entity.Lecture
import com.pryjda.app.exception.WrongLectureIdException
import com.pryjda.app.model.request.LectureRequestDTO
import com.pryjda.app.model.response.LectureResponseDTO
import com.pryjda.app.repository.LectureRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LectureServiceImpl @Autowired constructor(val lectureRepository: LectureRepository,
                                                val converter: ObjectConverter) : LectureService {
//    override fun getLecturesPaginated(page: Int, size: Int) {
//        return lectureRepository.findAllBy(PageRequest.of(page,size))
//    }

    override fun readLectures(): List<LectureResponseDTO> = lectureRepository
            .findAll()
            .map { converter.mapLectureToLectureResponseDTO(it) }


    override fun readSingleLecture(id: Long): LectureResponseDTO = lectureRepository
            .findById(id)
            .map { converter.mapLectureToLectureResponseDTO(it) }
            .orElseThrow { WrongLectureIdException("Wrong id number for lecture") }

    override fun createLecture(lectureRequestDTO: LectureRequestDTO): LectureResponseDTO {
        var lecture: Lecture = converter.mapLectureRequestDTOtoLecture(lectureRequestDTO)
        lectureRepository.save(lecture)
        return converter.mapLectureToLectureResponseDTO(lecture)
    }

    @Transactional
    override fun updateLectureById(id: Long, lectureRequestDTO: LectureRequestDTO): LectureResponseDTO = lectureRepository
            .findById(id)
            .map {
                if (lectureRequestDTO.description != null) it.description = lectureRequestDTO.description
                if (lectureRequestDTO.title != null) it.title = lectureRequestDTO.title
                if (lectureRequestDTO.lecturer != null) it.lecturer = lectureRequestDTO.lecturer
                converter.mapLectureToLectureResponseDTO(it)
            }
            .orElseThrow { WrongLectureIdException("Wrong id number for lecture") }

    override fun deleteLectureById(id: Long) = lectureRepository
            .findById(id)
            .map { lectureRepository.deleteById(id) }
            .orElseThrow { WrongLectureIdException("Wrong id number for lecture") }
}