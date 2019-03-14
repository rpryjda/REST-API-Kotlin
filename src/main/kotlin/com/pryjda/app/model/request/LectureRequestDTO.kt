package com.pryjda.app.model.request

import java.time.LocalDateTime

data class LectureRequestDTO(val title: String?,
                             val description: String?,
                             val lecturer: String?,
                             val date: LocalDateTime?)