package com.mercadolivro.model

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Role
import jakarta.persistence.*

@Entity(name = "customer")
data class CustomerModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id será gerado automaticamente
    var id: Int? = null,

    @Column
    var name: String?,

    @Column
    var email: String,

    @Column
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus,

    @Column
    val password: String,

    @Column(name = "role") // Nome da coluna no banco de dados
    @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")]) // Temos que utilizar o @ColletionTable pois essa coluna não esta na tabela customer e sim em uma tabela separada e usamos o joinColumns para fazer a ligação entre as tabelas com o id do cliente
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER) // ElementCollection indica que é uma coleção de elementos simples ou embutidos, e o fetch = FetchType.EAGER significa que ele trará os dados de email, perfil e busca as informações na tabela de profile
    @Enumerated(EnumType.STRING) // Define que o tipo de enum será armazenado como String no banco de dados
    var roles: Set<Role> = setOf() // Define o perfil do cliente, padrão é vazio (sem perfil definido) - Set<Profile> = coleção de perfis que o cliente pode ter (ADMIN, CUSTOMER, etc.
)