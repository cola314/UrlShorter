package org.goodgoodgood.urlshorter.security.jwt

import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig(
    @Value("\${jwt.secret}")
    val secret: String,

    @Value("\${jwt.expiration}")
    val expiration: Long,
) {
    val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256
}