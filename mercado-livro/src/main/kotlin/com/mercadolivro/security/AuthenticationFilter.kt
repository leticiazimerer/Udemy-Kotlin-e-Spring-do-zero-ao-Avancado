package com.mercadolivro.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mercadolivro.controller.request.LoginRequest
import com.mercadolivro.exceptions.AuthenticationException
import com.mercadolivro.repository.CustomerRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository,
): UsernamePasswordAuthenticationFilter(authenticationManager) { // UsernamePasswordAuthenticationFilter explica ao spring como vamos realizar o login

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java) // pegamos a request e transformamos o body dela no nosso LoginRequest
            val id = customerRepository.findByEmail(loginRequest.email)?.id // recebemos o email e a senha e depois chamamos o repository para pegar o id atraves do email
            val authToken = UsernamePasswordAuthenticationToken(id, loginRequest.senha) // Criamos um token de autenticação com o id do usuário e a senha, que será usado pelo Spring Security para autenticar o usuário
            return authenticationManager.authenticate(authToken) // o próprio spring verifica se o usuário existe e se está td certo
        } catch (ex: Exception) {
            throw AuthenticationException("Falha ao logar.", "999")
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        val id = (authResult.principal as UserCustomDetails).id
        val token = jwtUtil.generateToken(id)
        response.addHeader("Authorization", "Bearer $token")
    }
}