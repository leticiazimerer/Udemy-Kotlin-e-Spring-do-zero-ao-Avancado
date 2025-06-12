package com.mercadolivro.model

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Role
import jakarta.persistence.*

@Entity(name = "customer")
data class CustomerModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false)
    var name: String?,

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus = CustomerStatus.ATIVO,

    @Column(nullable = false)
    var password: String = "",

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    var roles: Set<Role> = setOf()
)