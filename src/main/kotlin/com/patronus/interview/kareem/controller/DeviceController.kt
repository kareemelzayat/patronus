package com.patronus.interview.kareem.controller

import com.patronus.interview.kareem.advice.RequiresAdmin
import com.patronus.interview.kareem.domain.Device
import com.patronus.interview.kareem.domain.User
import com.patronus.interview.kareem.modal.AssignUserDto
import com.patronus.interview.kareem.modal.DeviceDto
import com.patronus.interview.kareem.service.DeviceService
import com.patronus.interview.kareem.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/devices")
class DeviceController(
    private val deviceService: DeviceService,
    private val userService: UserService
) {

    @PostMapping
    @RequiresAdmin
    fun create(@RequestHeader("X-User-ID") principalId: Long, @RequestBody deviceDto: DeviceDto) : ResponseEntity<DeviceDto> {
        return ResponseEntity(deviceService.create(deviceDto, User()).toDto(), HttpStatus.CREATED)
    }

    @GetMapping("/{uuid}")
    fun get(@RequestHeader("X-User-ID") principalId: Long, @PathVariable uuid: UUID): ResponseEntity<DeviceDto> {
        val user: User = userService.getById(principalId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val device: Device = deviceService.getByUser(uuid, user) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(device.toDto(), HttpStatus.OK)
    }

    @PutMapping("/{uuid}")
    @RequiresAdmin
    fun update(@RequestHeader("X-User-ID") principalId: Long, @PathVariable uuid: UUID, @RequestBody assignUserDto: AssignUserDto): ResponseEntity<DeviceDto> {
        val adminUser: User = userService.getById(principalId)!!
        val user: User = userService.getById(assignUserDto.userId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val device: Device = deviceService.getByUser(uuid, adminUser) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(deviceService.assignToUser(device, user).toDto(), HttpStatus.OK)
    }

    @GetMapping
    fun list(@RequestHeader("X-User-ID") principalId: Long, pageable: Pageable): ResponseEntity<Page<DeviceDto>> {
        val user: User = userService.getById(principalId) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)

        if (!user.isAdmin()) {
            return ResponseEntity(deviceService.listByUser(user, pageable).map { it.toDto() }, HttpStatus.OK)
        }

        return ResponseEntity(deviceService.list(pageable).map { it.toDto() }, HttpStatus.OK)
    }
}