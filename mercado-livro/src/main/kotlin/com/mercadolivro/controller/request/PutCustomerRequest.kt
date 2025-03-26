package com.mercadolivro.controller.request

data class PutCustomerRequest (
    // Define os dados que podem ser alterados em um cliente.
    var name: String,
    var email: String
)