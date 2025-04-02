package com.mercadolivro.controller.response

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.CustomerModel
import java.math.BigDecimal

data class BookResponse (
    var id: Int? = null, // O id começa como null porque ele será preenchido apenas quando o livro for salvo no banco
    var name: String,
    var price: BigDecimal,
    var customer: CustomerModel? = null, // O livro pode ou não ter um cliente associado (null) e se um cliente comprar o livro, essa propriedade será preenchida com um CustomerModel, representando o comprador
    var status: BookStatus? = null
)