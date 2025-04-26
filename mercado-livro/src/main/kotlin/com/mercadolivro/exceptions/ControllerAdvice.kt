package com.mercadolivro.exceptions

import com.mercadolivro.controller.response.ErrorResponse
import com.mercadolivro.controller.response.FieldErrorResponse
import com.mercadolivro.enums.Errors
import com.mercadolivro.extension.BadRequestException
import com.mercadolivro.extension.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
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

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex:MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error =  ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(), // O Unprocessable Entity indica que o servidor entende o tipo de conteúdo da entidade da requisição, e a sintaxe da requisição esta correta, mas não foi possível processar as instruções presentes. Nesse caso, entendemos que mandaram um email sem o @, mas não é um email válido
            Errors.ML001.message,
            Errors.ML001.code,
            ex.bindingResult.fieldErrors.map { FieldErrorResponse(it.defaultMessage ?: "invalid", it.field) } // por meio do map, vamos pegar todos nossos erros e retornar eles todos de uma vez
        )
        return ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}