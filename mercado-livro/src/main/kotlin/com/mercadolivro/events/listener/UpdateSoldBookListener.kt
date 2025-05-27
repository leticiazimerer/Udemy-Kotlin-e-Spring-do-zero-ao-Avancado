package com.mercadolivro.events.listener

    import com.mercadolivro.events.PurchaseEvent
    import com.mercadolivro.service.BookService
    import com.mercadolivro.service.PurchaseService
    import java.util.UUID
    import org.springframework.context.event.EventListener
    import org.springframework.stereotype.Component

    @Component
    class UpdateSoldBookListener(private val bookService: BookService) { // Classe que escuta o evento de compra e atualiza o status dos livros vendidos

        @EventListener
        fun listen(purchaseEvent: PurchaseEvent) { // Método que escuta o evento de compra
            bookService.purchase(purchaseEvent.purchaseModel.books) // Chama o método purchase do BookService para atualizar o status dos livros comprados
        }
    }