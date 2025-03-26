package com.mercadolivro.controller.request

data class PostCustomerRequest (
    // Define os dados necessários para a criação de um cliente.
    var name: String,
    var email: String
)