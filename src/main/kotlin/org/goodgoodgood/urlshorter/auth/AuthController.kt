package org.goodgoodgood.urlshorter.auth

import org.goodgoodgood.urlshorter.auth.dto.GetAccountResponseDto
import org.goodgoodgood.urlshorter.auth.dto.LoginRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/auth")
@RestController
class AuthController(
    private val accountService: AccountService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody loginRequestDto: LoginRequestDto
    ): ResponseEntity<String> {
        return ResponseEntity
            .ok()
            .body(accountService.login(loginRequestDto))
    }

    @GetMapping("/account")
    fun getAccount(
        authentication: Authentication
    ) : ResponseEntity<GetAccountResponseDto> {
        val username = authentication.name

        return ResponseEntity
            .ok()
            .body(accountService.getAccount(username))
    }

}