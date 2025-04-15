package com.mercadolivro.extension

class NotFoundException(override val message: String, val errorCode: String): Exception() { // Colocamos overide para sobrescerveer, já que message já é um atributo de Exception
}