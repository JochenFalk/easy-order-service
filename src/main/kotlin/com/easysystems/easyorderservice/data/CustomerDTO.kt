package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.Authenticate
import mu.KLogging

data class CustomerDTO(
    override var id: Int?=0,
    override var name: String,
    var tableId: Int=0,
    var items: ArrayList<ItemDTO> = ArrayList()
) : UserDTO(id, name) {

    companion object : KLogging()

    init {
        logger.debug("Customer created with ID: $id and Name: $name")
    }

    fun addCustomerToTabletop(table: TabletopDTO, code: String) : Boolean {

        val isAuthenticated = Authenticate.authenticateTable(table, code)

        if (isAuthenticated)
        {
            if (table.id != null)
            {
                this.tableId = table.id!!
                table.customersDTO.add(this)
                logger.debug("Customer ${this.name} is added to table ${table.id}")
            }
            logger.debug("Customer ${this.name} failed to authenticate because table ID is null: ${table.id}")
        } else {
            logger.debug("Customer ${this.name} failed to authenticate table ${table.id}")
        }

        return isAuthenticated
    }
}