package com.mercadolivro.model

import javax.persistence.*

@Entity(name = "customer")
data class CustomerModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id será gerado automaticamente
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String
)