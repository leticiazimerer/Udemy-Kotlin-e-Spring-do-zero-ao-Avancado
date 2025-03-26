package com.mercadolivro.service

import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class CustomerService(
    val customerRepository: CustomerRepository // Dependência do CustomerRepository
) {

    fun getAll(name: String?): List<CustomerModel> { // Retorna uma lista de acordo com o nome (que pode ser null)
        name?.let { // 4* Se o parâmetro name não for nulo (name?.let), ele busca no banco de dados todos os clientes cujo nome contenha o valor de name. Para isso, ele usa o método findByNameContaining que está definido no repositório.
            return customerRepository.findByNameContaining(it) // 5* Usa o método findByNameContaining e retorna de acordo com o nome
        }
        return customerRepository.findAll().toList() // Se o name = null, retorna todos os clientes usando o findAll()
    }

    fun create(customer: CustomerModel) { // Recebe o obj CustomerModel e usa o repositório para salvar o novo cliente
        customerRepository.save(customer)
    }

    fun getById(id: Int): CustomerModel {
        return customerRepository.findById(id).orElseThrow() // "orElseThrow" lança uma exception caso nao ache nenhum registro e o findById(id) retorna um Optional<CustomerModel>, ou seja, pode ou não encontrar o cliente. O método orElseThrow() lança uma exceção caso o cliente não seja encontrado. Essa exceção pode ser tratada em outro lugar do sistema
    }

    fun update(customer: CustomerModel) {
        // Primeiro, ele verifica se o cliente já existe no banco com o id fornecido usando o método existsById(customer.id!!). Se o cliente não existir, ele lança uma exceção
        // O !! indica que estamos assumindo que o id não será nulo. Se o id for nulo, um NullPointerException será gerado.
        if(!customerRepository.existsById(customer.id!!)){
            throw Exception()
        }

        customerRepository.save(customer)
    }

    fun delete(id: Int) {
        if(!customerRepository.existsById(id)){ // verifica se o cliente existe com existsById(id). Se não encontrar o cliente, lança uma exceção.
            throw Exception()
        }

        customerRepository.deleteById(id)
    }
}

/*
4* Como funciona o let em name?.let {}?

name?.let {
    return customerRepository.findByNameContaining(it)
}

O let é uma função de extensão no Kotlin que pode ser chamada em qualquer objeto que não seja nulo. Ele é usado para executar um bloco de código
com o objeto it, onde it representa o valor do objeto dentro do bloco.

Se o parâmetro name for não nulo, o valor de name é passado para o bloco de código e o valor é atribuído à variável it.

Dentro do bloco de código, você pode usar it para acessar o valor de name e passar ele para o método findByNameContaining(it).

Se name for nulo, o bloco let não será executado e o código continuará para a próxima linha.

Resumo: let permite que você execute um bloco de código com um objeto não nulo. No caso, se name for não nulo, o bloco é executado e it contém o valor de name.
*/

/*
3* O que é esse it dentro do let?

return customerRepository.findByNameContaining(it)

O it é uma variável implícita em Kotlin que representa o objeto sobre o qual você está operando.

Dentro de um bloco let, o valor do objeto é automaticamente atribuído à variável it (você não precisa definir o nome dessa variável).

No seu exemplo, o it é o valor de name, e ele é passado como argumento para o método findByNameContaining(it).

Exemplo de como o it funciona: Se name for "Leticia", então o código seria equivalente a:

val name = "Leticia"
name?.let {
    return customerRepository.findByNameContaining("Leticia")
}
Ou seja, it substitui diretamente o valor de name.
*/
