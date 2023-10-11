package com.patronus.interview.kareem.service

import com.patronus.interview.kareem.domain.Device
import com.patronus.interview.kareem.domain.User
import com.patronus.interview.kareem.modal.DeviceDto
import com.patronus.interview.kareem.repository.DeviceRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeviceService(
    private val deviceRepository: DeviceRepository,
) {

    fun create(deviceDto: DeviceDto, user: User): Device {
        val newDevice = Device(deviceDto.serialNumber, deviceDto.phone, deviceDto.model)
        return deviceRepository.save(newDevice)
    }

    fun getByUser(uuid: UUID, user: User): Device? {
        return if (user.isAdmin()) deviceRepository.findByIdOrNull(uuid) else deviceRepository.findByUuidAndUser(uuid, user)
    }

    fun assignToUser(device: Device, user: User): Device {
        device.user = user
        return deviceRepository.save(device)
    }

    fun list(pageable: Pageable): Page<Device> {
        return deviceRepository.findAll(pageable)
    }

    fun listByUser(user: User, pageable: Pageable): Page<Device> {
        return deviceRepository.findAllByUser(user, pageable)
    }
}