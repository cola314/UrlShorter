package org.goodgoodgood.urlshorter.auth.dto

import org.goodgoodgood.urlshorter.auth.Account

data class GetAccountResponseDto(
    val username: String,
    val email: String,
)

fun Account.toDto(): GetAccountResponseDto {
    return GetAccountResponseDto(
        username = username,
        email = email,
    )
}
