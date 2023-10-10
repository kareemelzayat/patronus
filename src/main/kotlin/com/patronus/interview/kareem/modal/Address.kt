package com.patronus.interview.kareem.modal

abstract class Address(
    val street: String,
    val streetNo: String,
    val zip: String?,
    val city: String,
    val country: String
) {
    abstract override fun toString(): String
}