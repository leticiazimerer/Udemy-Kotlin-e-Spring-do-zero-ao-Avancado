package com.mercadolivro.controller.request

import java.math.BigDecimal

data class PutBookRequest(
    // Define os dados que podem ser alterados em um livro.
    var name: String?,
    var price: BigDecimal?
)