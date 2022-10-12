package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.CustomerDTO
import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.services.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/customers")
class CustomerController(val customerService: CustomerService){

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody customerDTO: CustomerDTO) : CustomerDTO {
        return customerService.createCustomer(customerDTO)
    }

    @GetMapping("/{id}")
    fun retrieveCustomerById(@PathVariable("id") id: Int): CustomerDTO {
        return customerService.retrieveCustomerById(id)
    }

    @GetMapping
    fun retrieveAllCustomers() : ArrayList<CustomerDTO> {
        return customerService.retrieveAllCustomers()
    }

    @PutMapping("/{id}")
    fun updateCustomer(@RequestBody customerDTO: CustomerDTO, @PathVariable("id") id: Int): CustomerDTO {
        return customerService.updateCustomer(id, customerDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable("id") id: Int) {
        return customerService.deleteCustomer(id)
    }

//    @RequestMapping(path = ["/delete_customer/{customerId}"], method = [RequestMethod.GET])
//    fun deleteCustomer(@PathVariable("customerId") customerId: Int) : Boolean {
//
//        return customerService.deleteCustomer(customerId)
//    }
}