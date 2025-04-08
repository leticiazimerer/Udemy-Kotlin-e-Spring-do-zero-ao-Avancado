package com.mercadolivro.exceptions

import com.mercadolivro.controller.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

// Aqui ficará concentrado nossos erros
@ControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(Exception::class)
    fun handlerException(ex:Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error =  ErrorResponse(
            400,
            "Esse recurso não existe.",
            "0001",
            null
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}