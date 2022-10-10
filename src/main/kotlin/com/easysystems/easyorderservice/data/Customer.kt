package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.Authenticate
import mu.KLogging

data class Customer(
    override val id: Int = mainUserList.size + 1,
    override var name: String,
    var tableId: Int = 0,
    var items: ArrayList<Item> = ArrayList()
) : User(id, name) {

    init {
        mainUserList.add(this)
        logger.info("Customer created with ID: $id and Name: $name")
    }

    companion object : KLogging()

    fun addCustomerToTable(table: Table, code: String) : Boolean {

        val isAuthenticated = Authenticate.authenticateTable(table, code)

        if (isAuthenticated)
        {
            this.tableId = table.id
            table.customerList.add(this)
            logger.info("Customer ${this.name} is added to table ${table.id}")
        } else {
            logger.warn("Customer ${this.name} failed to authenticate table ${table.id}")
        }

        return isAuthenticated
    }
}