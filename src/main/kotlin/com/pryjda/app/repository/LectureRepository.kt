package com.pryjda.app.repository

import com.pryjda.app.entity.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LectureRepository : JpaRepository<Lecture, Long>{
// fun findAllBy(pageable: Pageable)
}