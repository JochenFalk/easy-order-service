package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.*
import com.easysystems.easyorderservice.entities.Customer
import com.easysystems.easyorderservice.entities.Item
import com.easysystems.easyorderservice.exceptions.CustomerNotFoundException
import com.easysystems.easyorderservice.repositories.CustomerRepository
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CustomerService(val customerRepository: CustomerRepository, val authenticationService: AuthenticationService) {

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
            CustomerDTO(it.id, it.name)
        }
    }

    fun retrieveCustomerById(id: Int): CustomerDTO {

        val customer = customerRepository.findById(id)

        return if (customer.isPresent) {
            customer.get().let {
                CustomerDTO(it.id, it.name)
            }
        } else {
            throw CustomerNotFoundException("No customer found for given id: $id")
        }
    }

    fun retrieveAllCustomers(): ArrayList<CustomerDTO> {

        return customerRepository.findAll()
            .map {
                CustomerDTO(it.id, it.name)
            } as ArrayList<CustomerDTO>
    }

    fun updateCustomer(id: Int, customerDTO: CustomerDTO): CustomerDTO {

        val customer = customerRepository.findById(id)

        return if (customer.isPresent) {
            customer.get().let {
                it.name = customerDTO.name
                customerRepository.save(it)

                CustomerDTO(it.id, it.name)
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

//    fun addCustomerToTabletop(customerId: Int, tabletopId: Int, tabletopCode: String): Boolean {
//
//        val customer = customerRepository.findById(customerId)
//
//        if (customer.isPresent) {
//
//            customer.get().let {
//
//                if (authenticationService.tabletop(tabletopId, tabletopCode)) {
//
//                    it.tableId = tabletopId
//                    customerRepository.save(it)
//
//                    logger.info("Customer ${it.name} is added to table $tabletopId")
//                    return true
//                } else {
//                    logger.info("Customer ${it.name} was not added! Authentication for table $tabletopId failed")
//                    return false
//                }
//            }
//        } else {
//            throw CustomerNotFoundException("No customer found for given id: $customerId")
//        }
//    }
}