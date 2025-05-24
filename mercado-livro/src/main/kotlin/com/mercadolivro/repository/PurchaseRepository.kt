package com.mercadolivro.repository

import com.mercadolivro.model.PurchaseModel
import org.springframework.data.repository.CrudRepository

interface PurchaseRepository: CrudRepository<PurchaseModel, Int> { // Aqui estamos definindo o PurchaseRepository como uma interface que estende CrudRepository, permitindo operações CRUD para PurchaseModel

}
