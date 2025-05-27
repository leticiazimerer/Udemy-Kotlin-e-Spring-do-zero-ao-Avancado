package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.extension.BadRequestException
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    fun create(purchaseModel: PurchaseModel) {
        val invalidBooks = purchaseModel.books.filter { it.status != BookStatus.ATIVO } // Filtra os livros que não estão ativos
        if (invalidBooks.isNotEmpty()) { // Se houver livros inválidos, lança uma exceção
            throw BadRequestException(
                Errors.ML102.message.format(invalidBooks.joinToString { it.status?.name ?: "UNKNOWN" }), // Formata a mensagem de erro com os status dos livros inválidos
                Errors.ML102.code
            )
        }

        purchaseRepository.save(purchaseModel)
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }
}
