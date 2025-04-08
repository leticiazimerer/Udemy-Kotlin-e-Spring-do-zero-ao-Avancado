package com.mercadolivro.controller.response

data class ErrorResponse(
    var httpCode: Int,
    var message: String,
    var internalCode: String,
    var errors: List<FieldErrorResponse>? // Retorna o obj 'FieldErrorResponse' que trará infos sobre os erros que foram passados para nós na requisição
)