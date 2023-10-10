package com.patronus.interview.kareem.repository

import com.patronus.interview.kareem.domain.Device
import com.patronus.interview.kareem.domain.User
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DeviceRepository : CrudRepository<Device, UUID>, PagingAndSortingRepository<Device, UUID> {
    fun findAllByUser(user: User, pageable: Pageable): Page<Device>
}