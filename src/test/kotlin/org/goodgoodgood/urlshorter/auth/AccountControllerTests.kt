package org.goodgoodgood.urlshorter.auth

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.goodgoodgood.urlshorter.auth.dto.LoginRequestDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@AutoConfigureMockMvc
@SpringBootTest
class AccountControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `login 성공시 jwt 토큰 발급`() {
        val loginRequestDto = LoginRequestDto("test", "test")
        val json = jacksonObjectMapper().writeValueAsString(loginRequestDto)

        mockMvc.post("/api/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }
            .andExpect {
                status { isOk() }
            }
            .andDo { print() }
    }

    @Test
    fun `login 설패시 401 반환`() {
        val loginRequestDto = LoginRequestDto("test", "wrongPassword")
        var json = jacksonObjectMapper().writeValueAsString(loginRequestDto)

        mockMvc.post("/api/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = json
        }
            .andExpect {
                status { isUnauthorized() }
            }
            .andDo { print() }
    }

    @Test
    fun `GET account 인증 안된 상태일 경우 401`() {
        mockMvc.get("/api/auth/account")
            .andExpect {
                status { isUnauthorized() }
            }
    }
}