package com.patronus.interview.kareem.service

import com.patronus.interview.kareem.domain.User
import com.patronus.interview.kareem.modal.AddressDto
import com.patronus.interview.kareem.modal.AddressWithState
import com.patronus.interview.kareem.modal.AddressWithoutZip
import com.patronus.interview.kareem.modal.UserDto
import com.patronus.interview.kareem.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun create(userDto: UserDto): User {
        val newUser = User(
            userDto.firstName,
            userDto.lastName,
            getAddress(userDto.address),
            userDto.birthday,
            "email@gmail.com",
            "strong-password",
            userDto.role
        )
        return userRepository.save(newUser)
    }

    fun getById(userId: Long): User? {
        return userRepository.findByIdOrNull(userId)
    }

    fun list(pageable: Pageable): Page<User> {
        return userRepository.findAll(pageable)
    }

    /**
     * Factory method to retrieve the correct concrete Address classs
     */
    private fun getAddress(addressDto: AddressDto): String {
        if (addressDto.zip == null) {
            return AddressWithoutZip(addressDto).toString()
        }

        return AddressWithState(addressDto).toString()
    }
}