package com.pryjda.app.model.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

class LectureResponseDTO(val id: Long?,
                         val title: String?,
                         val description: String?,
                         val lecturer: String?,
                         @JsonFormat(pattern = "yyyy-MM-dd HH:mm") val date: LocalDateTime?)