package com.patronus.interview.kareem.repository

import com.patronus.interview.kareem.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long>, PagingAndSortingRepository<User, Long>