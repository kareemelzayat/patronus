package com.patronus.interview.kareem.modal

import com.patronus.interview.kareem.domain.User
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class UserDto(
    val id: Long?,
    val firstName: String,
    val lastName: String,
    val address: AddressDto,

    @field:[DateTimeFormat(pattern = "yyyy-MM-dd")]
    val birthday: LocalDate,

    val role: User.Role,

    val devices: Set<DeviceDto>? = emptySet(),
) {
    constructor(
        firstName: String,
        lastName: String,
        address: AddressDto,
        birthday: LocalDate,
        role: User.Role,
        devices: Set<DeviceDto>? = emptySet()
    ): this(0L, firstName, lastName, address, birthday, role, devices)
}
