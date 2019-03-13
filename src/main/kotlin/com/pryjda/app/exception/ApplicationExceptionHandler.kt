package com.pryjda.app.exception

import com.pryjda.app.model.response.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class ApplicationExceptionHandler {

    @ExceptionHandler
    fun handleWrongLectureIdException(exe: WrongLectureIdException): ResponseEntity<ErrorMessage> {
        val errorMessage: ErrorMessage = ErrorMessage(LocalDateTime.now(), exe.localizedMessage)
        return ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND)
    }
}