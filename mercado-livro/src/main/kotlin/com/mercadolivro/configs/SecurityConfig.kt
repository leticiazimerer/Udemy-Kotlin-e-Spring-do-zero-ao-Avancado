package com.mercadolivro.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.http.HttpMethod

@Configuration // Define que esta classe contém configurações da aplicação
@EnableWebSecurity // Ativa a segurança da web
class SecurityConfig {

    private val PUBLIC_MATCHERS = arrayOf<String>() // Endpoints públicos acessíveis por qualquer método HTTP (GET, POST, etc.)

    private val PUBLIC_POST_MATCHERS = arrayOf("/customer") // Endpoints públicos apenas para requisições POST

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Desabilita CSRF (recomendado em APIs REST)
            .cors { it.disable() } // Desabilita CORS (pode configurar manualmente se quiser aceitar outros domínios)
            .sessionManagement { // Define a política de sessão da aplicação
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // STATELESS = cada requisição é independente (sem armazenar sessão no servidor)
            }
            .authorizeHttpRequests { // Define as regras de autorização da API
                it
                    .requestMatchers(*PUBLIC_MATCHERS).permitAll() // Permite livre acesso a rotas públicas (GET, PUT, etc.)
                    .requestMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll() // Permite POST em /customer sem autenticação
                    .anyRequest().authenticated() // Qualquer outra rota precisa de autenticação
            }

        return http.build() // Finaliza e aplica a configuração
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder() // Utiliza o algoritmo BCrypt (seguro e recomendado)
    }
}
