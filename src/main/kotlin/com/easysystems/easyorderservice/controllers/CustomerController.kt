package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.Customer
import com.easysystems.easyorderservice.services.CustomerService
import mu.KLogging
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/customers")
class CustomerController(val customerService: CustomerService){

    companion object : KLogging()

    @RequestMapping(path = ["/create_customer/{name}"], method = [RequestMethod.GET])
    fun createCustomer(@PathVariable("name") name: String) : Customer {

        return customerService.createCustomer(name)
    }

    @RequestMapping(path = ["/delete_customer/{customerId}"], method = [RequestMethod.GET])
    fun deleteCustomer(@PathVariable("customerId") customerId: Int) : Boolean {

        return customerService.deleteCustomer(customerId)
    }

    @RequestMapping(path = ["/add_customer_to_table/{customerId}/{tableId}/{code}"], method = [RequestMethod.GET])
    fun addCustomerToTable(@PathVariable customerId: Int, @PathVariable tableId: Int, @PathVariable code: String) : Boolean {

        return customerService.addCustomerToTable(customerId, tableId, code)
    }

}