package com.easysystems.easyorderservice.data

import mu.KLogging

data class EmployeeDTO (
    override var id: Int?=0,
    override var name: String,
    var password: String,
    var tabletopsDTO: ArrayList<Int> = ArrayList()
) : UserDTO(id, name) {

    companion object : KLogging()

    init {
        logger.info("Employee created with ID: $id and Name: $name")
    }
}