package com.patronus.interview.kareem.domain

import com.patronus.interview.kareem.modal.DeviceDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToOne
import jakarta.persistence.Version
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.util.*

@Entity
class Device(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID,

    @Version
    val version: Int,

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateCreated: LocalDateTime,

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val lastUpdated: LocalDateTime,

    val serialNumber: String,

    val phone: String,

    val model: String,

    @ManyToOne
    @JoinTable(
        name = "patronus_user_devices",
        joinColumns = [JoinColumn(name = "device_uuid")],
        inverseJoinColumns = [JoinColumn(name = "patronus_user_id")]
    )
    var user: User?
) {
    constructor(
        serialNumber: String,
        phone: String,
        model: String
    ): this(UUID.randomUUID(), 0, LocalDateTime.MIN, LocalDateTime.MIN, serialNumber, phone, model, null)

    fun toDto(): DeviceDto = DeviceDto(uuid, serialNumber, phone, model, user?.id)
}