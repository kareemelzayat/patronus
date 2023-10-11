package com.patronus.interview.kareem.controller

import com.patronus.interview.kareem.domain.User
import com.patronus.interview.kareem.modal.AddressDto
import com.patronus.interview.kareem.modal.UserDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.getForObject
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.data.domain.Page
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.net.URI
import java.net.URL
import java.time.LocalDate


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation::class)
class UserControllerTest {

    @Value(value = "\${local.server.port}")
    private val port = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    @Order(0)
    fun `create creates a new admin user`() {
        // given
        val userDto = UserDto(
            "Kareem",
            "Elzayat",
            AddressDto(
                "Gotzkowskystr.",
                "28",
                null,
                "10555",
                "Berlin",
                "Berlin",
                "DEU"
            ),
            LocalDate.parse("1994-12-08"),
            User.Role.ADMIN
        )

        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        // a header that represents an existing admin user
        headers.add("X-User-ID", "1")

        val httpEntity: HttpEntity<UserDto> = HttpEntity(userDto, headers)

        // when
        val responseEntity: ResponseEntity<UserDto> = restTemplate.postForEntity<UserDto>("http://localhost:$port/api/users", httpEntity)
        val result: UserDto = responseEntity.body ?: Assertions.fail()

        // then
        assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        assertEquals(userDto.firstName, result.firstName)
        assertEquals(userDto.role, result.role)
    }

    fun `create creates a new non-admin user`() {
        // given
        val userDto = UserDto(
            "Kareem",
            "Elzayat",
            AddressDto(
                "Gotzkowskystr.",
                "28",
                null,
                "10555",
                "Berlin",
                "Berlin",
                "DEU"
            ),
            LocalDate.parse("1994-12-08"),
            User.Role.NON_ADMIN
        )

        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        headers.add("X-User-ID", "1")

        val httpEntity: HttpEntity<UserDto> = HttpEntity(userDto, headers)

        // when
        val responseEntity: ResponseEntity<UserDto> = restTemplate.postForEntity<UserDto>("http://localhost:$port/api/users", httpEntity)
        val result: UserDto = responseEntity.body ?: Assertions.fail()

        // then
        assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        assertEquals(userDto.firstName, result.firstName)
        assertEquals(userDto.role, result.role)
    }

    @Test
    @Order(1)
    @Disabled("Depends on the RequiresAdmin annotation to work")
    fun `create returns error`() {
        // given
        val userDto = UserDto(
            "Kareem",
            "Elzayat",
            AddressDto(
                "Gotzkowskystr.",
                "28",
                null,
                "10555",
                "Berlin",
                "Berlin",
                "DEU"
            ),
            LocalDate.parse("1994-12-08"),
            User.Role.NON_ADMIN
        )

        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        headers.add("X-User-ID", "1")

        val httpEntity: HttpEntity<UserDto> = HttpEntity(userDto, headers)

        // when
        val responseEntity: ResponseEntity<UserDto> = restTemplate.postForEntity<UserDto>("http://localhost:$port/api/users", httpEntity)

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.statusCode)
    }

    @Test
    @Order(2)
    @Disabled("Depends on the RequiresAdmin annotation to work")
    fun `list returns available users`() {
        // given
        val headers: MultiValueMap<String, String> = LinkedMultiValueMap()
        headers.add("X-User-ID", "1")

        val requestEntity = RequestEntity<Page<UserDto>>(headers, HttpMethod.GET, URI("http://localhost:$port/api/users"))

        // when
        val responseEntity: ResponseEntity<Page<UserDto>> = restTemplate.exchange(requestEntity)
        val result: Page<UserDto> = responseEntity.body ?: Assertions.fail()

        // then
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertEquals(2, result.content.size)
        assertEquals(listOf(User.Role.ADMIN, User.Role.NON_ADMIN), result.content.map { it.role })
    }
}