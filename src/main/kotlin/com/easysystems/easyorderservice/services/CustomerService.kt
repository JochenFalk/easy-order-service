package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.*
import mu.KLogger
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CustomerService {

    @Value("\${message}")
    lateinit var message: String

    val userRepository = UserRepository()
    val tableRepository = TableRepository()

    companion object : KLogging()

    init {

    }

    fun createCustomer(name: String) : Customer {

        logger.warn {"Add stuff to create customer!"}
        return Customer(name = name)
    }

    fun deleteCustomer(id: Int) : Boolean {

        val customer = userRepository.getById(id) as Customer?
        if (customer != null)
        {
            logger.warn("$message $customer deleted")
            return true
        } else {
            logger.warn { "Customer with id $id not found!" }
            return false
        }
    }

    fun addCustomerToTable(customerId: Int, tableId: Int, code: String) : Boolean {

        val customer = userRepository.getById(customerId) as Customer?
        val table = tableRepository.getById(tableId)

        if (customer != null && table != null)
        {
            return customer.addCustomerToTable(table, code)
        } else {
            logger.warn { "Customer not added to table! Customer: $customer, Table: $table" }
            return  false
        }
    }

}