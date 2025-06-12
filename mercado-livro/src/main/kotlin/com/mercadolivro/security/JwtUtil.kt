package com.mercadolivro.security

import com.mercadolivro.exceptions.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {
    @Value("\${jwt.expirationTime}") // spring pega os valores do application.properties
    private val expiration: Long? = null

    @Value("\${jwt.secret}")
    private val secret: String? = null
    fun generateToken(id: Int): String {
        val now = Date()
        val expirationDate = Date(now.time + expiration!!) // tempo atual + tempo de expiração

        return Jwts.builder()
            .setSubject(id.toString())
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, secret!!.toByteArray())
            .compact()
    }

    fun isValidToken(token: String): Boolean {
        val claims = getClaims(token)
        if (claims.subject == null || claims.expiration == null || Date().after(claims.expiration)) {
            return false
        }
        return true
    }

    private fun getClaims(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(secret!!.toByteArray()).parseClaimsJws(token).body
        } catch (ex: Exception) {
            throw AuthenticationException("Token inválido ou expirado.", "999")
        }
    }

    fun getSubject(token: String): String {
        return getClaims(token).subject
    }
}