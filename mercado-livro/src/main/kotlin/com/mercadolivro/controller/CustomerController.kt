package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customer") // Todos os endpoints terão o prefixo '/customer'
// Os controllers são responsáveis por receber requisições HTTP e chamar os serviços.
class CustomerController(
    val customerService : CustomerService // Recebe CustomerService como dependência (é onde contem a lógica do negócio)
) {

    @GetMapping
    fun getAll(@RequestParam name: String?): List<CustomerModel> { // Permite filtrar clientes pelo nome se o parametro for passado na URLb(/customer?name=Leticia)
        return customerService.getAll(name) // Chama 'customerService.getAll(name)' p buscar os clientes e retorna uma lista
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Resposta 201 CREATED
    fun create(@RequestBody customer: PostCustomerRequest) { // Recebe os dados no corpo da requisição e converte para PostCustomerRequest
       customerService.create(customer.toCustomerModel()) // 3* customer.toCustomerModel() = Converte PostCustomerRequest para CustomerModel
    }

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: Int): CustomerModel { // Captura o {id} da URL e passa como argumento e retorna um obj do tipo 'CustomerModel'
        return customerService.findById(id) // Busca o cliente no banco e retorna
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 indicando que a atualização foi bem-sucedida sem retornar um corpo de resposta
    fun update(@PathVariable id: Int, @RequestBody customer: PutCustomerRequest) { // Recebe os dados no corpo da requisição e converte para CustomerModel
       customerService.update(customer.toCustomerModel(id)) // Atualiza mantendo o id antigo
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        customerService.delete(id) // Chama o serviço para deletar o cliente do banco
    }
}

/*
3* Por que precisamos converter PostCustomerRequest para CustomerModel?

PostCustomerRequest: Representa os dados recebidos na requisição HTTP (DTO)
CustomerModel: Representa a entidade que será armazenada no banco de dados

1. Quando alguém faz uma requisição POST /customer, o corpo da requisição pode ser assim:

{
    "name": "João Silva",
    "email": "joao@email.com"
}

O Spring converte esse JSON para um objeto PostCustomerRequest:

data class PostCustomerRequest(
    val name: String,
    val email: String
)
Mas esse PostCustomerRequest NÃO contém todas as informações que o banco de dados precisa!
Por exemplo, ele não tem o id e outros campos importantes, como status do cliente

2. Para salvar um cliente no banco, precisamos criar um CustomerModel completo.

Por isso, usamos uma função de extensão para converter PostCustomerRequest para CustomerModel = 'toCustomerModel()'

Essa conversão preenche os dados que estavam faltando:

- id = null → O banco vai gerar um ID automaticamente.
- status = CustomerStatus.ACTIVE → Define um status padrão ao criar um novo cliente.

3. Agora que temos um CustomerModel, podemos chamar o serviço para salvar no banco:

customerService.create(customer.toCustomerModel())
O serviço (CustomerService) cuida da regra de negócio e salva o cliente no banco:

fun create(customer: CustomerModel) {
    customerRepository.save(customer) // Salva no banco usando JPA
}
*/

/*
Explicação sobre as conversões (toCustomerModel)
Por que converter PostCustomerRequest e PutCustomerRequest para CustomerModel?
Os DTOs (PostCustomerRequest e PutCustomerRequest) são usados para receber dados da API, mas o
banco de dados trabalha com CustomerModel.

A função de conversão toCustomerModel() transforma os DTOs em CustomerModel, garantindo que
todos os campos necessários sejam preenchidos corretamente antes de salvar ou atualizar no banco.

Exemplo de conversão para atualização (PutCustomerRequest → CustomerModel):

fun PutCustomerRequest.toCustomerModel(id: Int): CustomerModel {
    return CustomerModel(
        id = id,
        name = this.name,
        email = this.email
    )
}
Isso garante que o ID do cliente seja mantido e apenas os campos name e email sejam alterados.
*/
