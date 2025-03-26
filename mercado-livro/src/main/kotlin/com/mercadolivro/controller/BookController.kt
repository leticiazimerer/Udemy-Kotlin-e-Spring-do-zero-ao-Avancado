package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.extension.toBookModel
import com.mercadolivro.model.BookModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController // Define que essa classe é um controller REST
@RequestMapping("book")
class BookController(
    val bookService: BookService, // Recebe bookService e customerService como dependências injetadas automaticamente pelo Spring
    val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Define que ao criar um livro, a resposta terá o status 201 CREATED
    fun create(@RequestBody request: PostBookRequest) { // O corpo da requisição deve conter um PostBookRequest (um DTO que representa os dados para criar um livro)
        val customer = customerService.getById(request.customerId) // Busca o cliente no sistema usando o ID que veio na requisição
                                                                   // e garante que o cliente existe antes de criar o livro.
        bookService.create(request.toBookModel(customer)) // 1* Converte a requisição para BookModel e cria o livro
    }

    @GetMapping
    fun findAll() : List<BookModel> {
        return bookService.findAll()
    }

    @GetMapping("/active")
    fun findActives(): List<BookModel> = bookService.findActives()
    // =
    // @GetMapping("/active")
    // fun findActives(): List<BookModel> {
    //    return bookService.findActives()
    // }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) : BookModel { // "@PathVariable id: Int:" - Captura o {id} da URL e passa para a função
        return  bookService.findById(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Resposta sera 204 NO CONTENT (sem conteúdo no corpo da resposta)
    fun delete(@PathVariable id : Int) {
        bookService.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun uptade(@PathVariable id : Int, @RequestBody book : PutBookRequest) { // O corpo da requisição deve conter os dados para atualizar o livro (o objeto 'PutBookRequest' é recebido como parâmetro na função)
        val bookSaved = bookService.findById(id) // 2*
        bookService.update(book.toBookModel(bookSaved)) // bookService.update salva as alterações
    }
}

/*
1* A conversão para BookModel é necessária porque o BookService trabalha diretamente com essa entidade,
enquanto o objeto PostBookRequest é apenas um DTO, utilizado para receber dados da requisição HTTP.

Os DTOs (PostBookRequest e PutBookRequest) são criados para representar os dados da requisição HTTP de
uma forma mais simples e desacoplada do modelo de dados do banco. Já o BookModel é a entidade que será
salva no banco de dados.

A conversão acontece para que possamos:

- Manter a separação de responsabilidades → Os DTOs são usados apenas para comunicação externa (entrada
e saída de dados da API), enquanto os Models (BookModel) são usados na lógica de negócio e persistência.

- Evitar expor diretamente o modelo de banco → Se usássemos BookModel diretamente na requisição, alterações
no banco poderiam impactar a API.

- Incluir a referência ao cliente → PostBookRequest traz apenas customerId (um número), enquanto BookModel
precisa da instância completa de CustomerModel.

val customer = customerService.getById(request.customerId) = Buscamos o CustomerModel correspondente ao customerId.
bookService.create(request.toBookModel(customer)) = Usamos a função toBookModel(customer), que converte PostBookRequest em BookModel.
*** toBookModel realiza a conversão e pega os dados de PostBookRequest, adiciona o CustomerModel e retorna um BookModel.*/


/*
2* estamos buscando no banco de dados o livro que queremos atualizar.

Por que precisamos buscar o livro antes de atualizar?
Para garantir que o livro existe no banco antes de tentar atualizar

O PutBookRequest só contém os novos dados (name e price), mas BookModel pode ter mais atributos, como id e customer

A função findById(id) busca o livro pelo ID:
val bookSaved = bookService.findById(id)

Se o ID não existir, a função provavelmente lançará uma exceção.
Exemplo:
Se o ID existir:
bookService.findById(10) retorna um BookModel existente.

Se o ID não existir:
Provavelmente lançará um erro do tipo "Livro não encontrado".*/
