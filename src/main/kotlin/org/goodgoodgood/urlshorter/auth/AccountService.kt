package org.goodgoodgood.urlshorter.auth

import org.goodgoodgood.urlshorter.auth.dto.GetAccountResponseDto
import org.goodgoodgood.urlshorter.auth.dto.LoginRequestDto
import org.goodgoodgood.urlshorter.auth.dto.toDto
import org.goodgoodgood.urlshorter.auth.exception.LoginFailException
import org.goodgoodgood.urlshorter.auth.exception.UserNotFoundException
import org.goodgoodgood.urlshorter.security.jwt.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
) {

    @PostConstruct
    fun init() {
        accountRepository.save(Account("test", "test", passwordEncoder.encode("test")));
    }

    @Transactional(readOnly = true)
    fun login(loginRequest: LoginRequestDto): String {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password, null)
            )
        } catch (e: BadCredentialsException) {
            throw LoginFailException("fail to login")
        }

        return jwtService.createToken(loginRequest.username)
    }

    fun getAccount(username: String): GetAccountResponseDto {
        return accountRepository.findByUsername(username)?.toDto()
            ?: throw UserNotFoundException()
    }
}