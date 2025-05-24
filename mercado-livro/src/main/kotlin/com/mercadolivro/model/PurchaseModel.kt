package com.mercadolivro.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(name = "purchase")
data class PurchaseModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que o banco de dados gerará o ID automaticamente (Auto Increment)
    var id: Int? = null, // O id começa como null porque ele será preenchido apenas quando o livro for salvo no banco

    @ManyToOne // Muitas comprar para 1 cliente
    @JoinColumn(name = "customer_id") // Define a coluna que faz a relação com a tabela de clientes
    val customer: CustomerModel,

    @ManyToMany // Uma compra pode ter muitos livros e um livro pode estar em muitas compras
    @JoinTable(
        name = "purchase_book", // Nome da tabela de junção
        joinColumns = [JoinColumn(name = "purchase_id")], // Coluna que faz a relação com a tabela de compras
        inverseJoinColumns = [JoinColumn(name = "book_id")] // Coluna que faz a relação com a tabela de livros
    )
    val books: List<BookModel>, // Usamos List para manter a ordem dos livros na compra

    @Column
    val nfe: String? = null, // Nota Fiscal Eletrônica, pode ser null se não for gerada
    @Column
    val price: BigDecimal,

    @Column(name = "created_at")
    val createdAt: LocalDateTime // Para sabermos quando a compra foi realizada
)

//    Compra 1     Livros 1 2 3
//    1            1            // Compra 1 tem os livros 1, 2 e 3, ou seja, 3 registros na tabela de junção
//    1            2
//    1            3