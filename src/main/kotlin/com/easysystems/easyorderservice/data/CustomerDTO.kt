package com.easysystems.easyorderservice.data

import mu.KLogging

data class CustomerDTO(
    override var id: Int? = 0,
    override var name: String,
    var tableId: Int = 0,
    var items: ArrayList<Int> = ArrayList()
) : UserDTO(id, name) {

    companion object : KLogging()

    init {
        logger.debug("Customer created with ID: $id and Name: $name")
    }
}