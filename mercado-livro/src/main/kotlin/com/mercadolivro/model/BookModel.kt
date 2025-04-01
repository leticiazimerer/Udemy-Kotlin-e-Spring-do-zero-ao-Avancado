package com.mercadolivro.model

import com.mercadolivro.enums.BookStatus
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name = "book") // Define que essa classe é uma entidade do banco
data class BookModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que o banco de dados gerará o ID automaticamente (Auto Increment)
    var id: Int? = null, // O id começa como null porque ele será preenchido apenas quando o livro for salvo no banco

    @Column
    var name: String,

    @Column
    var price: BigDecimal,



    @ManyToOne // muitos livros para 1 usuário
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null // O livro pode ou não ter um cliente associado (null) e se um cliente comprar o livro, essa propriedade será preenchida com um CustomerModel, representando o comprador.
) {
    // Como sobrescrever um valor:
    @Column
    @Enumerated(EnumType.STRING) // O status do livro é armazenado como texto no banco.
    var status: BookStatus? = null
        set(value) { // novo valor que vamos utilizar
            if (field == BookStatus.DELETADO || field == BookStatus.CANCELADO) { // field é o valor atual do nosso campo
                throw Exception("Não é possivel alterar um livro com o status ${field}")
            }
            field = value // caso nao seja deletado e nem cancelado, ele deixa alterar o status
        }

    // como tiramos o status de cima, precisa ter um construtor
    constructor(id : Int? = null,
                name : String,
                price: BigDecimal,
                customer: CustomerModel? = null,
                status: BookStatus?) : this(id, name, price, customer) { // "this(id, name, price, customer)" invoca o construtor que tem em cima
                    this.status = status
                }
}