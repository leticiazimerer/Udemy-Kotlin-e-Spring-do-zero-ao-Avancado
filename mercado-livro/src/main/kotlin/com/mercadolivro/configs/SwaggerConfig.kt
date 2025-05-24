package com.mercadolivro.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2 // Anotação que ativa o Swagger na aplicação
class SwaggerConfig {
    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.mercadolivro.controller"))
        .paths(PathSelectors.any()) // Seleciona todos os endpoints da aplicação
        .build() // Cria uma instância do Docket, que é a configuração principal do Swagger
        .apiInfo(
            ApiInfoBuilder() // Configurações adicionais da API, como título, descrição, etc
                .title("Mercado Livro API")
                .description("API para gerenciamento de livros e clientes")
                .build())
}

// 1. Após fazer essas alterações, ir no Google e acessar o endereço http://localhost:8080/swagger-ui.html
// 2. Você verá a interface do Swagger, onde poderá explorar os endpoints da sua API
// 3. Você pode testar os endpoints diretamente da interface do Swagger, enviando requisições e visualizando as respostas