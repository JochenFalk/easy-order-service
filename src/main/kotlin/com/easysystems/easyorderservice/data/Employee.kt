package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.Authenticate
import mu.KLogging

data class Employee (
    override val id: Int = mainUserList.size + 1,
    override var name: String,
    var password: String,
    var tables: ArrayList<Table> = ArrayList()
) : User(id, name) {

    companion object : KLogging()

    init {
        mainUserList.add(this)
        logger.info("Employee created with ID: $id and Name: $name")
    }

    fun assignTableToEmployee(table: Table, name: String, password: String)
    {
        if (Authenticate.authenticateEmployee(this, name, password))
        {
            this.tables.add(table)
            logger.info("Table ${this.id} is assigned to ${this.name}")
        }
    }
}