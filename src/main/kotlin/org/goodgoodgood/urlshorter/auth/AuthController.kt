package org.goodgoodgood.urlshorter.auth

import org.goodgoodgood.urlshorter.auth.dto.GetAccountResponseDto
import org.goodgoodgood.urlshorter.auth.dto.LoginRequestDto
import org.goodgoodgood.urlshorter.auth.exception.UnauthorizedException
import org.springframework.http.HttpStatus
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
        authentication: Authentication?
    ) : ResponseEntity<GetAccountResponseDto> {
        if (authentication == null)
            throw UnauthorizedException();

        return ResponseEntity
            .ok()
            .body(accountService.getAccount(authentication.name))
    }

}