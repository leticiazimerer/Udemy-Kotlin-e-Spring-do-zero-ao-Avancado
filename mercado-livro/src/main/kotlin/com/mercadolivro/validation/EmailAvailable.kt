package com.mercadolivro.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [EmailAvailableValidator::class]) // @EmailAvailable será validada pela classe EmailAvailableValidator e ssa validação será executada automaticamente quando o campo for processado com @Valid
@Retention(AnnotationRetention.RUNTIME) // A anotação estará disponível em tempo de execução, o que é necessário para o Spring poder utilizá-la durante a validação
@Target(AnnotationTarget.FIELD) // Essa anotação só pode ser usada em atributos (fields) de classes, não em classes inteiras ou funções
annotation class EmailAvailable(
    val message: String = "Email já cadastrado.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

// groups é usado quando você quer validar diferentes regras dependendo do contexto. Por exemplo:
// Quando criar o cliente, validar @NotEmpty, @Email, @EmailAvailable.
// Mas quando atualizar, talvez não queira validar @EmailAvailable, porque o e-mail já foi validado na criação.

// payload permite você anexar metadados personalizados à validação. Isso é útil para sistemas de validação avançados, como:
// Auditar quais validações foram aplicadas.
// Mostrar na UI com base na severidade (por exemplo: aviso vs. erro crítico).
// Classificar tipos de erro.