package com.pryjda.app.exception

import com.pryjda.app.model.response.ErrorMessage
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
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

    @ExceptionHandler
    fun handleWrongUserIdException(exe: WrongUserIdException): ResponseEntity<ErrorMessage> {
        val errorMessage: ErrorMessage = ErrorMessage(LocalDateTime.now(), exe.localizedMessage)
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleValidationException(exe: MethodArgumentNotValidException): ResponseEntity<ErrorMessage> {
        val errorMessage: ErrorMessage = ErrorMessage(LocalDateTime.now(), exe.bindingResult.fieldError?.defaultMessage.toString())
        return ResponseEntity(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY)
    }

//    @ExceptionHandler
//    fun handleInvalidFormatException(exe: InvalidFormatException): ResponseEntity<ErrorMessage> {
//        val errorMessage: ErrorMessage = ErrorMessage(LocalDateTime.now(), exe.localizedMessage)
//        return ResponseEntity(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY)
//    }

    @ExceptionHandler
    fun handleValidationExceptionHttpMessageNotReadable(exe: HttpMessageNotReadableException): ResponseEntity<ErrorMessage> {

        var localizedMessage = exe.localizedMessage

        if (localizedMessage.indexOf("value not one of declared Enum instance names:") != -1) {
            val enumValuesString: String = StringUtils.substringBetween(localizedMessage, "value not one of declared Enum instance names: ", ";")
            localizedMessage = "Uncorrect constant values, values should be taken from: $enumValuesString"
            //com.fasterxml.jackson.databind.exc.InvalidFormatException
        }

        val errorMessage: ErrorMessage = ErrorMessage(LocalDateTime.now(), localizedMessage)
        return ResponseEntity(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}