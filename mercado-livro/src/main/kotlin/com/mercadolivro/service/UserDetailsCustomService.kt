package com.mercadolivro.service

import com.mercadolivro.exceptions.AuthenticationException
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomService(
    private val customerRepository: CustomerRepository
): UserDetailsService {
    override fun loadUserByUsername(id: String?): UserDetails {
        val customer = id?.toInt()?.let {
            customerRepository.findById(it)
                .orElseThrow { AuthenticationException("Usuário não encontrado.", "999") }
        } ?: throw AuthenticationException("ID do usuário é nulo.", "998")
        return UserCustomDetails(customer)
    }
}