package org.goodgoodgood.urlshorter.security.jwt

import org.goodgoodgood.urlshorter.security.UserAccountDetailsService
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    private val jwtService: JwtService,
    private val userAccountDetailsService: UserAccountDetailsService,
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String = request.getHeader("Authorization")
            ?.substring("Bearer ".length)
            ?: return filterChain.doFilter(request, response)

        try {
            val username = jwtService.parseUsername(token)
                ?: return filterChain.doFilter(request, response)

            val accountUserDetails = userAccountDetailsService.loadUserByUsername(username)
            val authentication =
                UsernamePasswordAuthenticationToken(accountUserDetails, null, accountUserDetails.authorities)
            SecurityContextHolder.getContext().authentication = authentication

            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            logger.info("invalid token", e)
            response.status = HttpServletResponse.SC_UNAUTHORIZED
        }
    }

}