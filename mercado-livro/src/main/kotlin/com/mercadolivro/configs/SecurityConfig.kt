package com.mercadolivro.configs

import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.AuthenticationFilter
import com.mercadolivro.security.AuthorizationFilter
import com.mercadolivro.security.JwtUtil
import com.mercadolivro.enums.Role
import com.mercadolivro.service.UserDetailsCustomService
import io.swagger.models.HttpMethod
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil
): WebSecurityConfigurationAdapter() {

    private val PUBLIC_MATCHERS = arrayOf<String>()

    private val PUBLIC_POST_MATCHERS = arrayOf("/customer") // Definimos os endpoints públicos que não precisam de autenticação

    private val ADMIN_MATCHERS = arrayOf("/admin/**") // todas as rotas que começam com /admin já vai incluir tambem
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder)
    }

    override fun configure(http: HttpSecurity): SecurityFilterChain { // Usamos o SecurityFilterChain para configurar as regras de segurança da aplicação e o HttpSecurity para definir as regras de autorização e autenticação
        http.cors().and().csrf().disable()// Desabilita a proteção CSRF (Cross-Site Request Forgery), que é uma medida de segurança para evitar ataques de falsificação de solicitação entre sites. É comum desabilitar em APIs RESTful, mas deve ser usado com cautela.
        http.authorizeRequests() // Configura as regras de autorização para as requisições HTTP
            .antMatchers(*PUBLIC_MATCHERS).permitAll()
            .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
            .antMatchers(*ADMIN_MATCHERS).hasAuthority(Role.ADMIN.description) // Apenas usuários com a role ADMIN podem acessar os endpoints de admin
            .anyRequest().authenticated()
        http.addFilter(AuthenticationFilter(authenticationManager(), customerRepository, jwtUtil))
        http.addFilter(AuthorizationFilter(authenticationManager(), userDetails, jwtUtil)) // Adiciona os filtros de autenticação e autorização
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Aqui estamos falando que as requisições serão independentes e não tem nada a ver com as anteriores, podendo ser de usuários diferentes
    }

    override fun configure(webSecurity: WebSecurity) {
        webSecurity.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**") // Ignora as requisições para a documentação Swagger
    }

    @Bean
    fun corsConfig(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config) // Configura o CORS para permitir requisições de qualquer origem, cabeçalho e método
        return CorsFilter(source)
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
