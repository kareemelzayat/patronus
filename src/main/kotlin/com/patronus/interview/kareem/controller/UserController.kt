package com.patronus.interview.kareem.controller

import com.patronus.interview.kareem.advice.RequiresAdmin
import com.patronus.interview.kareem.domain.User
import com.patronus.interview.kareem.modal.UserDto
import com.patronus.interview.kareem.service.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
@Validated
@RequiresAdmin
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun create(@RequestHeader("X-User-ID") userId: Long, @RequestBody @Valid userDto: UserDto): ResponseEntity<UserDto> {
        return ResponseEntity(userService.create(userDto).toDto(), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun get(@RequestHeader("X-User-ID") userId: Long, @PathVariable id: Long): ResponseEntity<UserDto> {
        val user: User = userService.getById(id) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(user.toDto(), HttpStatus.OK)
    }

    @GetMapping
    fun list(@RequestHeader("X-User-ID") userId: Long, pageable: Pageable): ResponseEntity<Page<UserDto>> {
        return ResponseEntity(userService.list(pageable).map { it.toDto() }, HttpStatus.OK)
    }
}