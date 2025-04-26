package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull // <-- Corrigido aqui
import java.math.BigDecimal

data class PostBookRequest (
    // Define os dados necessários para a criação de um livro.
    @field:NotEmpty(message = "Nome deve ser informado")
    var name: String,

    @field:NotNull(message = "Preço deve ser informado")
    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Int
)

// data class é uma classe especial para armazenar dados e o Kotlin gera automaticamente métodos úteis: toString(), equals(), hashCode(), copy() componentN() (para desestruturação)
// data class é usada porque facilita a manipulação de dados.
// Os parênteses () definem o construtor primário, tornando a classe mais compacta e idiomática.
// Data classes geralmente não precisam de {} porque os atributos já são definidos no construtor.

// data class:

//data class PostBookRequest (
//    var name: String,
//    var price: BigDecimal,
//    var customerId: Int
// )

// class normal:

// class PostBookRequest {
//    var name: String = ""
//    var price: BigDecimal = BigDecimal.ZERO
//    var customerId: Int = 0
// }

