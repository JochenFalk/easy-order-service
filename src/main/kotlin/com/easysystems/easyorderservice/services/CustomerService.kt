package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.*
import com.easysystems.easyorderservice.entities.Customer
import com.easysystems.easyorderservice.entities.Item
import com.easysystems.easyorderservice.exceptions.CustomerNotFoundException
import com.easysystems.easyorderservice.repositories.CustomerRepository
import com.easysystems.easyorderservice.repositories.TabletopRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CustomerService(val customerRepository: CustomerRepository, val tabletopRepository: TabletopRepository) {

    @Value("\${message}")
    lateinit var message: String

    companion object : KLogging()

    fun createCustomer(customerDTO: CustomerDTO): CustomerDTO {

        val customer = customerDTO.let {
            Customer(null, it.name)
        }

        customerRepository.save(customer)

        logger.info("New customer created: $customer")

        return customer.let {
            CustomerDTO(it.id, it.name, it.tableId, it.items as ArrayList<ItemDTO>)
        }
    }

    fun retrieveCustomerById(id: Int): CustomerDTO {

        val customer = customerRepository.findById(id)

        return if (customer.isPresent) {
            customer.get().let {
                CustomerDTO(it.id, it.name, it.tableId, it.items as ArrayList<ItemDTO>)
            }
        } else {
            throw CustomerNotFoundException("No customer found for given id: $id")
        }
    }

    fun retrieveAllCustomers(): ArrayList<CustomerDTO> {

        return customerRepository.findAll()
            .map {
                CustomerDTO(it.id, it.name, it.tableId, it.items as ArrayList<ItemDTO>)
            } as ArrayList<CustomerDTO>
    }

    fun updateCustomer(id: Int, customerDTO: CustomerDTO): CustomerDTO {

        val customer = customerRepository.findById(id)

        return if (customer.isPresent) {
            customer.get().let {
                it.name = customerDTO.name
                it.tableId = customerDTO.tableId
                it.items = customerDTO.items as ArrayList<Item>
                customerRepository.save(it)

                CustomerDTO(it.id, it.name, it.tableId, it.items as ArrayList<ItemDTO>)
            }
        } else {
            throw CustomerNotFoundException("No customer found for given id: $id")
        }
    }

    fun deleteCustomer(id: Int) {

        val customer = customerRepository.findById(id)

        if (customer.isPresent) {
            customer.get().let {
                customerRepository.deleteById(id)
            }
        } else {
            throw CustomerNotFoundException("No customer found for given id: $id")
        }
    }

//    fun deleteCustomer(id: Int): Boolean {

//        try {
//            customerRepository.deleteById(id)
//            logger.info("Customer with id: $id is deleted")
//            return true
//        } catch (ex: Exception) {
//            logger.warn("Customer with id: $id is not deleted! Exception: $ex")
//            return false
//        }
//    }

//    fun addCustomerToTable(customerId: Int, tableId: Int, code: String): Boolean {
//
//        val customer = customerRepository.findById(customerId)
//        val table = tabletopRepository.findById(tableId)
//
//        if (customer != null && table != null) {
//            return customerDTO.addCustomerToTabletop(table, code)
//        } else {
//            logger.warn { "Customer not added to table! Customer: $customerDTO, Table: $table" }
//            return false
//        }
//    }
}