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

    @Column
    @Enumerated(EnumType.STRING) // O status do livro é armazenado como texto no banco.
    var status: BookStatus? = null,

    @ManyToOne // muitos livros para 1 usuário
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null // O livro pode ou não ter um cliente associado (null) e se um cliente comprar o livro, essa propriedade será preenchida com um CustomerModel, representando o comprador.
)