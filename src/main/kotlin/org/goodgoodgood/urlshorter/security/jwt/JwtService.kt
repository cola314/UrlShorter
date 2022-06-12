package org.goodgoodgood.urlshorter.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import javax.annotation.PostConstruct

@Service
class JwtService(
    private val jwtConfig: JwtConfig
) {
    private lateinit var key: Key

    @PostConstruct
    private fun init() {
        key = Keys.hmacShaKeyFor(jwtConfig.secret.toByteArray())
    }

    fun createToken(username: String): String {
        val claims: Claims = Jwts.claims()
        claims["username"] = username

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + jwtConfig.expiration))
            .signWith(key, jwtConfig.signatureAlgorithm)
            .compact();
    }

    fun parseUsername(token: String): String? {
        val claims = getClaims(token)
        return claims["username"] as String
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}