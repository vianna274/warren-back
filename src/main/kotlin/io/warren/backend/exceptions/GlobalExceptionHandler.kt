package io.warren.backend.exceptions

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebInputException

@ControllerAdvice
class GlobalExceptionHandler {

//    @ExceptionHandler(Exception::class)
//    fun generateException(ex: Exception): ResponseStatusException {
//        return ResponseStatusException(HttpStatus.HTTP_VERSION_NOT_SUPPORTED, ex.message)
//    }

//    @ExceptionHandler(ServerWebInputException::class)
//    fun generateServerWebInputException(ex: ServerWebInputException) {
//        val cause = ex.mostSpecificCause
//        val exception = when (cause) {
//            is MissingKotlinParameterException -> ResponseStatusException(HttpStatus.BAD_REQUEST, cause.originalMessage)
//            is InvalidFormatException -> ResponseStatusException(HttpStatus.BAD_REQUEST, cause.originalMessage)
//            else -> ResponseStatusException(HttpStatus.BAD_REQUEST, cause.message)
//        }
//
//        throw exception
//    }
}