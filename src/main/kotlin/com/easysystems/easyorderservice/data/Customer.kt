package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.Authenticate
import com.easysystems.easyorderservice.repositories.mainUserListDTO
import mu.KLogging

data class Customer(
    override var id: Int? = mainUserListDTO.size + 1,
    override var name: String,
    var tableId: Int = 0,
    var itemDTOs: ArrayList<ItemDTO> = ArrayList()
) : UserDTO(id, name) {

    init {
        mainUserListDTO.add(this)
        logger.debug("Customer created with ID: $id and Name: $name")
    }

    companion object : KLogging()

    fun addCustomerToTabletop(table: TabletopDTO, code: String) : Boolean {

        val isAuthenticated = Authenticate.authenticateTable(table, code)

        if (isAuthenticated)
        {
            if (table.id != null)
            {
                this.tableId = table.id!!
                table.customers.add(this)
                logger.debug("Customer ${this.name} is added to table ${table.id}")
            }
            logger.debug("Customer ${this.name} failed to authenticate because table ID is null: ${table.id}")
        } else {
            logger.debug("Customer ${this.name} failed to authenticate table ${table.id}")
        }

        return isAuthenticated
    }
}