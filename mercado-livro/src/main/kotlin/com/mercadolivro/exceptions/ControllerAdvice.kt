package com.mercadolivro.exceptions

import com.mercadolivro.controller.response.ErrorResponse
import com.mercadolivro.extension.BadRequestException
import com.mercadolivro.extension.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

// Aqui ficará concentrado nossos erros
@ControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(NotFoundException::class)
    fun handlerNotFoundException(ex:NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error =  ErrorResponse(
            HttpStatus.NOT_FOUND.value(), // = erro 404
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handlerBadRequestException(ex:BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error =  ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), // = erro 400
            ex.message,
            ex.errorCode,
            null
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}