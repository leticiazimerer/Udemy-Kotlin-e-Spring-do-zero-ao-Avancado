package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service

// Implementam a lógica do negócio
// Coordena as interações com o repositório
// Recebe requisições do controller
// Processa as regras de negócio e chama o repositório para persistir ou buscar dados do banco
@Service
class BookService(
    val bookRepository: BookRepository // Dependência permitindo que o service acesse os métodos p interagir com o BD
) {
    fun create(book: BookModel) { // Recebe o obj BookModel e chama o método save do repositório
        bookRepository.save(book)
    }

    fun findAll(): List<BookModel> { // função retornará uma lista
        return bookRepository.findAll().toList()
    }

    fun findActives(): List<BookModel> {
        return  bookRepository.findByStatus(BookStatus.ATIVO) // Chama o método findByStatus do repositório, passando o status ATIVO. O Spring Data JPA gera automaticamente a consulta SQL que seleciona os livros com esse status
    }

    fun findById(id : Int): BookModel { // Busca o livro por ID
        return bookRepository.findById(id).orElseThrow() // caso não exista, retorna um erro
    }

    fun delete(id: Int) {
        val book = findById(id)
        book.status = BookStatus.CANCELADO
        bookRepository.save(book)
        update(book)
    }

    fun update(book: BookModel) {
        bookRepository.save(book) // O save do repositório realiza tanto a criação quanto a atualização do livro. Se o livro já existe (já tem um ID), ele será atualizado. Caso contrário, será criado
    }
}