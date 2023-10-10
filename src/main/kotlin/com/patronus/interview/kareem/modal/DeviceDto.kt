package com.patronus.interview.kareem.modal

import jakarta.validation.constraints.Pattern
import java.util.*

data class DeviceDto(
    val uuid: UUID?,

    @Pattern(regexp = "^[a-zA-Z0-9]{16}\$")
    val serialNumber: String,

    @Pattern(regexp = "^[0-9]{10,11}\$")
    val phone: String,

    val model: String,

    val userId: Long?
)
