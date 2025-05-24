package com.mercadolivro.controller.request

import jakarta.validation.constraints.Positive
import org.jetbrains.annotations.NotNull

data class PostPurchaseRequest(

    @field:NotNull
    @field: Positive // Garante que o valor é positivo
    val customerId: Int,

    @field:NotNull
    val bookIds: Set<Int> // Usamos Set para receber apenas valores diferentes
)