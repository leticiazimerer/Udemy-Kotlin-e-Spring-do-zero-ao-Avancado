package com.mercadolivro.validation

import com.mercadolivro.service.CustomerService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

// EmailAvailableValidator recebe o CustomerService (precisa dele para validar se o email é válido) e extende da
// classe ConstraintValidator sendo que a EmailAvailable é a nossa notation e string pq é o tipo do atributo
// que vamos validar, que nesse caso, é o email

class EmailAvailableValidator(var customerService: CustomerService) :
    ConstraintValidator<EmailAvailable, String> {
        //Essa é a função principal que o Spring chama automaticamente para saber se o valor é válido
        //value é o valor do campo a ser validado (ou seja, o e-mail)
        //context é o contexto da validação (geralmente não usamos)
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if(value.isNullOrEmpty()) { // Se o e-mail estiver vazio ou nulo, a validação já falha aqui
            return false
        }
            //Chama o método emailAvailable no CustomerService para verificar se esse e-mail já existe no banco de dados.
            //O retorno será:
            //true: e-mail está livre → OK
            //false: e-mail já está em uso → FALHA
        return customerService.emailAvailable(value)
    }
}
