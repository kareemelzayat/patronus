package com.patronus.interview.kareem.modal

class AddressWithoutZip(
    private val addressExtra: String,
    street: String,
    streetNo: String,
    city: String,
    country: String
) : Address(street, streetNo, null, city, country) {
    constructor(addressDto: AddressDto): this (addressDto.addressExtra!!, addressDto.street, addressDto.streetNo, addressDto.city, addressDto.country)
    override fun toString(): String = "$street $streetNo, $addressExtra, $city, $country"
}