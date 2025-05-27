package com.mercadolivro.controller.mapper

import com.mercadolivro.controller.request.PostPurchaseRequest
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component // Usamos @Component para que o Spring possa gerenciar esta classe como um bean
class PurchaseMapper(
    private val bookService: BookService,
    private val customerService: CustomerService
) {
    fun toModel(request: PostPurchaseRequest): PurchaseModel { // Estamos definindo um método que converte o PostPurchaseRequest em um PurchaseModel
        val customer = customerService.findById(request.customerId) // Aqui buscamos o cliente pelo ID fornecido na requisição
        val books = bookService.findAllByIds(request.bookIds) // Aqui buscamos os livros pelos IDs fornecidos na requisição

        return PurchaseModel(
            customer = customer, // Atribuímos o cliente encontrado ao modelo de compra
            books = books.toMutableList(), // Converte a lista de IDs de livros para uma lista de BookModel
            price = books.sumOf { it.price }, // calcula o preço total da compra somando os preços de todos os livros
            createdAt = LocalDateTime.now()
        )
    }
}

