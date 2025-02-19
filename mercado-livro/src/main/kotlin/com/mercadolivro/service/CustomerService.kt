package com.mercadolivro.service

import com.mercadolivro.model.CustomerModel
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class CustomerService {
    val customers = mutableListOf<CustomerModel>()

    fun getAll(name: String?): List<CustomerModel> {
        name?.let {
            return customers.filter { it.nome.contains(name, true) }
        }
        return customers
    }

    fun createCustomer(customer: CustomerModel) {
        val id = if(customers.isEmpty()) {
            "1"
        } else {
            customers.last().id!!.toInt() + 1
        }.toString()
        customer.id = id
        customers.add(customer)
    }

    fun getCustomer(id: String): CustomerModel {
        return customers.filter { it.id == id }.first()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(customer: CustomerModel) {
        customers.filter { it.id == customer.id }.first().let {
            it.nome = customer.nome
            it.email = customer.email
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete( id: String) {
        customers.removeIf { it.id == id }
    }
}