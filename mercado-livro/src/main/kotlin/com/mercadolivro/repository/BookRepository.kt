package com.mercadolivro.repository

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import org.springframework.data.repository.CrudRepository

// É a interface que  acessa o BD
interface BookRepository : CrudRepository<BookModel, Int> { // Herda de CRUD com os métodos prontos (findAll, save...) e usa o INT para garantir que o ID será sempre um número válido
    abstract fun findByStatus(status: BookStatus) : List<BookModel> // Retorna uma liksta de BookModel = SELECT * FROM book WHERE status = 'ACTIVE';
}