package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.Positive
import org.jetbrains.annotations.NotNull

data class PostPurchaseRequest(

    @field:NotNull
    @field: Positive // Garante que o valor é positivo
    @JsonAlias("customer_id") // Permite que o campo seja recebido como "customer_id" no JSON)
    val customerId: Int,

    @field:NotNull
    @JsonAlias("book_ids")
    val bookIds: Set<Int> // Usamos Set para receber apenas valores diferentes
)