package com.mercadolivro.controller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("admin") // Todos os endpoints terão o prefixo '/customer'
// Os controllers são responsáveis por receber requisições HTTP e chamar os serviços.
class AdminController(
) {

    @GetMapping("/reports")
    fun report(): String{
        return "This is a report. Only Admin can see it!"
    }
}