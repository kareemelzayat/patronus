package com.patronus.interview.kareem.controller

import com.patronus.interview.kareem.advice.RequiresAdmin
import com.patronus.interview.kareem.modal.UserDto
import com.patronus.interview.kareem.service.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
@Validated
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @RequiresAdmin
    fun create(@RequestBody @Valid userDto: UserDto): UserDto {
        return userService.create(userDto).toDto()
    }

    @GetMapping
    @RequiresAdmin
    fun list(pageable: Pageable?): Page<UserDto> {
        return userService.list(pageable).map { it.toDto() }
    }
}