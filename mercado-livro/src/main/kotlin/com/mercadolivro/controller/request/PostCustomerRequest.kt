package com.mercadolivro.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PostCustomerRequest (
    // Define os dados necessários para a criação de um cliente.
    @field:NotEmpty(message = "Nome deve ser informado") // o field (serve para deixar explícito qie estamos colocando em um atributo) e aqui diz que não pode ser null e nem vazio
    var name: String,

    @field:Email(message = "Email deve ser válido") // o próprio spring tem validações para emails
    var email: String
)