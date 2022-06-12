package org.goodgoodgood.urlshorter.auth.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
class LoginFailException(message: String) : RuntimeException(message) {
}