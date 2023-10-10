package com.patronus.interview.kareem.domain

import com.patronus.interview.kareem.modal.AddressDto
import com.patronus.interview.kareem.modal.UserDto
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.persistence.Version
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "patronus_user")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Version
    val version: Int,

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val dateCreated: LocalDateTime,

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val lastUpdated: LocalDateTime,

    val firstName: String,

    val lastName: String,

    val address: String,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val birthday: LocalDate,

    @OneToMany
    @JoinTable(
        name = "patronus_user_devices",
        joinColumns = [JoinColumn(name = "patronus_user_id")],
        inverseJoinColumns = [JoinColumn(name = "device_uuid")]
    )
    val devices: MutableSet<Device>,

    val email: String,

    var password: String,

    @Enumerated(EnumType.STRING)
    val role: Role
) {
    constructor(): this("", "", "", LocalDate.MIN, "", "", Role.NON_ADMIN)
    constructor(
        firstName: String,
        lastName: String,
        address: String,
        birthday: LocalDate,
        email: String,
        password: String,
        role: Role
    ): this(0L, 0, LocalDateTime.MIN, LocalDateTime.MIN, firstName, lastName,  address, birthday, mutableSetOf(), email, password, role)

    fun toDto(): UserDto = UserDto(
        id,
        firstName,
        lastName,
        getAddressDto(),
        birthday,
        role,
        devices.map { it.toDto() }.toSet(),
    )

    fun isAdmin(): Boolean = role == Role.ADMIN

    enum class Role {
        ADMIN,
        NON_ADMIN
    }

    private fun getAddressDto(): AddressDto {
        val addressTokens: List<String> = address.replace(",", "").split(" ")
        if (address.matches(".*[0-9]{5}.*".toRegex())) {
            return AddressDto(addressTokens[0], addressTokens[1], null, addressTokens[2].trim(), addressTokens[3], addressTokens[4], addressTokens[5])
        }
        return AddressDto(addressTokens[0], addressTokens[1], addressTokens[2].trim(), null, addressTokens[3], addressTokens[4], addressTokens[5])
    }
}
