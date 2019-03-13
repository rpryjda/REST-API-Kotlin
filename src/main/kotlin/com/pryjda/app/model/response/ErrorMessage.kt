package com.pryjda.app.model.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ErrorMessage(@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") val time: LocalDateTime,
                        val message: String)