package com.mercadolivro.configs

import com.mercadolivro.controller.request.PostPurchaseRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController("/purchase")
class PurchaseController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Aqui estamos definindo o status HTTP 201 Created
    fun purchase(@RequestBody request: PostPurchaseRequest) { // Aqui usamos o @RequestBody para mapear o corpo da requisição para o objeto PostPurchaseRequest
    }
}