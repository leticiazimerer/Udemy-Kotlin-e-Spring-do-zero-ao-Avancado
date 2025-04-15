package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.extension.NotFoundException
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    fun findAll(pageable: Pageable): Page<BookModel> { // função retornará uma lista
        return bookRepository.findAll(pageable)
    }

    fun findActives(pageable: Pageable): Page<BookModel> {
        return  bookRepository.findByStatus(BookStatus.ATIVO, pageable) // Chama o metodo findByStatus do repositório, passando o status ATIVO. O Spring Data JPA gera automaticamente a consulta SQL que seleciona os livros com esse status
    }

    fun findById(id : Int): BookModel { // Busca o livro por ID
        return bookRepository.findById(id).orElseThrow{ NotFoundException(Errors.ML101.message.format(id), Errors.ML101.code) } // caso não exista, retorna nossa exception
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

    fun deleteByCustomer(customer : CustomerModel) {
        val books = bookRepository.findByCustomer(customer) // busca todos os livros que pertencem ao customer e salba em books
        for (book in books) { // Para cada registro na lista books vamos criar variável book e iterar sobre ela
            book.status = BookStatus.DELETADO
            bookRepository.saveAll(books) // salva a minha lista de livros
        }
    }
}