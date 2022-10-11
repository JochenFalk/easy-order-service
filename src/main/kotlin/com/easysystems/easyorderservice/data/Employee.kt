package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.Authenticate
import com.easysystems.easyorderservice.repositories.mainUserListDTO
import mu.KLogging

data class Employee (
    override var id: Int? = mainUserListDTO.size + 1,
    override var name: String,
    var password: String,
    var tabletopDTOS: ArrayList<TabletopDTO> = ArrayList()
) : UserDTO(id, name) {

    companion object : KLogging()

    init {
        mainUserListDTO.add(this)
        logger.info("Employee created with ID: $id and Name: $name")
    }

    fun assignTabletopToEmployee(tableTopDTO: TabletopDTO, name: String, password: String)
    {
        if (Authenticate.authenticateEmployee(this, name, password))
        {
            this.tabletopDTOS.add(tableTopDTO)
            logger.info("Table ${this.id} is assigned to ${this.name}")
        }
    }
}