package com.mercadolivro.events.listener

    import com.mercadolivro.events.PurchaseEvent
    import com.mercadolivro.service.PurchaseService
    import java.util.UUID
    import org.springframework.context.event.EventListener
    import org.springframework.scheduling.annotation.Async
    import org.springframework.stereotype.Component

    @Component
    class GenerateNfeListener(private val purchaseService: PurchaseService) {

        @Async
        @EventListener
        fun listen(purchaseEvent: PurchaseEvent) {
            val nfe = UUID.randomUUID().toString() // Gera um UUID aleatório como string
            val purchaseModel = purchaseEvent.purchaseModel.copy(nfe = nfe) // Cria uma cópia do modelo de compra com o NFE gerado
            purchaseService.update(purchaseModel) // Atualiza o modelo de compra com o NFE gerado
        }
    }