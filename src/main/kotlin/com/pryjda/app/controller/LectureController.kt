package com.pryjda.app.controller

import com.pryjda.app.entity.Lecture
import com.pryjda.app.service.LectureService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/lectures")
class LectureController(@Autowired val lectureService: LectureService) {

    @GetMapping
    fun readLectures(): List<Lecture> = lectureService.readLectures()

    @GetMapping("/{id}")
    fun readSingleLectureById(@PathVariable id: Long): Lecture = lectureService.readSingleLecture(id)

    @PostMapping
    fun createLecture(@RequestBody lecture: Lecture): Lecture = lectureService.createLecture(lecture)

    @PutMapping("/{id}")
    fun updateLectureById(@RequestBody lecture: Lecture,
                          @PathVariable id: Long): Lecture = lectureService.updateLectureById(id, lecture)

    @DeleteMapping("/{id}")
    fun deleteLectureById(id: Long) = lectureService.deleteLectureById(id)
}