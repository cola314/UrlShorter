package org.goodgoodgood.urlshorter.security

import org.goodgoodgood.urlshorter.auth.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserAccountDetailsService(
    private val accountRepository: AccountRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return accountRepository.findByUsername(username)
            ?.let { UserAccountDetails(it) }
            ?: throw UsernameNotFoundException("username not exists");
    }

}