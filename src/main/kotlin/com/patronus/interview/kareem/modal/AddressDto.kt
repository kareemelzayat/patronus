package com.patronus.interview.kareem.modal

data class AddressDto(
    val street: String,
    val streetNo: String,
    val addressExtra: String?,
    val zip: String?,
    val city: String,
    val state: String?,
    val country: String
)
