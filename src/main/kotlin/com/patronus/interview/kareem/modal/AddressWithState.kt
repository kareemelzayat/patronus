package com.patronus.interview.kareem.modal

class AddressWithState(
    private val state: String,
    street: String,
    streetNo: String,
    zip: String,
    city: String,
    country: String
) : Address(street, streetNo, zip, city, country) {
    constructor(addressDto: AddressDto): this (addressDto.state!!, addressDto.street, addressDto.streetNo, addressDto.zip!!, addressDto.city, addressDto.country)
    override fun toString(): String = "$street $streetNo, $zip $city, $state $country"
}