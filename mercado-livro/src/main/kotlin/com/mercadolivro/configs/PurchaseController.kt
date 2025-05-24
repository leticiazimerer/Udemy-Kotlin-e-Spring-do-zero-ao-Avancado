package com.mercadolivro.configs

import com.mercadolivro.controller.mapper.PurchaseMapper
import com.mercadolivro.controller.request.PostPurchaseRequest
import com.mercadolivro.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController("/purchase")
class PurchaseController(
    private val purchaseService: PurchaseService, // Aqui estamos injetando o PurchaseService, que será usado para processar a compra
    private val purchaseMapper: PurchaseMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Aqui estamos definindo o status HTTP 201 Created
    fun purchase(@RequestBody request: PostPurchaseRequest) { // Aqui usamos o @RequestBody para mapear o corpo da requisição para o objeto PostPurchaseRequest
        purchaseService.create(purchaseMapper.toModel(request)) // Aqui chamamos o método create do PurchaseService, passando o modelo de compra convertido a partir da requisição
    }
}