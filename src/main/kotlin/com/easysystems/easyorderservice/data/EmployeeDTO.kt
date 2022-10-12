package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.Authenticate
import mu.KLogging

data class EmployeeDTO (
    override var id: Int?=0,
    override var name: String,
    var password: String,
    var tabletopsDTO: ArrayList<TabletopDTO> = ArrayList()
) : UserDTO(id, name) {

    companion object : KLogging()

    init {
        logger.info("Employee created with ID: $id and Name: $name")
    }

    fun assignTabletopToEmployee(tableTopDTO: TabletopDTO, name: String, password: String)
    {
        if (Authenticate.authenticateEmployee(this, name, password))
        {
            this.tabletopsDTO.add(tableTopDTO)
            logger.info("Table ${this.id} is assigned to ${this.name}")
        }
    }
}