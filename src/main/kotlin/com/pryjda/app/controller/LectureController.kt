package com.pryjda.app.controller

import com.pryjda.app.model.request.LectureRequestDTO
import com.pryjda.app.model.response.LectureResponseDTO
import com.pryjda.app.service.LectureService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/lectures")
class LectureController(@Autowired val lectureService: LectureService) {

    @GetMapping
    fun readLectures(): List<LectureResponseDTO> = lectureService.readLectures()

    @GetMapping("/{id}")
    fun readSingleLectureById(@PathVariable id: Long): LectureResponseDTO = lectureService.readSingleLecture(id)

    @PostMapping
    fun createLecture(@RequestBody lecture: LectureRequestDTO): LectureResponseDTO = lectureService.createLecture(lecture)

    @PutMapping("/{id}")
    fun updateLectureById(@RequestBody lecture: LectureRequestDTO,
                          @PathVariable id: Long): LectureResponseDTO = lectureService.updateLectureById(id, lecture)

    @DeleteMapping("/{id}")
    fun deleteLectureById(@PathVariable id: Long) = lectureService.deleteLectureById(id)
}