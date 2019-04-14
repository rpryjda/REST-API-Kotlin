package com.pryjda.app.controller

import com.pryjda.app.model.request.LectureRequestDTO
import com.pryjda.app.model.response.LectureResponseDTO
import com.pryjda.app.service.LectureService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/lectures")
class LectureController(@Autowired val lectureService: LectureService) {

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    fun readLectures(): List<LectureResponseDTO> = lectureService.readLectures()

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    fun readSingleLectureById(@PathVariable id: Long): LectureResponseDTO = lectureService.readSingleLecture(id)

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun createLecture(@RequestBody lecture: LectureRequestDTO): LectureResponseDTO = lectureService.createLecture(lecture)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun updateLectureById(@RequestBody lecture: LectureRequestDTO,
                          @PathVariable id: Long): LectureResponseDTO = lectureService.updateLectureById(id, lecture)


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun deleteLectureById(@PathVariable id: Long) = lectureService.deleteLectureById(id)
}