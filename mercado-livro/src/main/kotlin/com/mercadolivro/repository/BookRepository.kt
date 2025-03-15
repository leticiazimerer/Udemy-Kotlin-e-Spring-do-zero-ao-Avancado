package com.mercadolivro.repository

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<BookModel, Int> {
    abstract fun findByStatus(status: BookStatus) : List<BookModel>
}