package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.CustomerDTO
import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.services.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/customers")
@Validated
class CustomerController(val customerService: CustomerService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody @Valid customerDTO: CustomerDTO): CustomerDTO {
        return customerService.createCustomer(customerDTO)
    }

    @GetMapping("/{id}")
    fun retrieveCustomerById(@PathVariable("id") id: Int): CustomerDTO {
        return customerService.retrieveCustomerById(id)
    }

    @GetMapping
    fun retrieveAllCustomers(): ArrayList<CustomerDTO> {
        return customerService.retrieveAllCustomers()
    }

    @PutMapping("/{id}")
    fun updateCustomer(@RequestBody @Valid customerDTO: CustomerDTO,
                       @PathVariable("id") id: Int): CustomerDTO {
        return customerService.updateCustomer(id, customerDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable("id") id: Int) {
        return customerService.deleteCustomer(id)
    }

//    @PutMapping("/{customerId}/{tabletopId}/{tabletopCode}")
//    fun addCustomerToTabletop(
//        @PathVariable tabletopId: Int,
//        @PathVariable customerId: Int,
//        @PathVariable tabletopCode: String
//    ): Boolean {
//        return customerService.addCustomerToTabletop(customerId, tabletopId, tabletopCode)
//    }

//    @RequestMapping(path = ["/delete_customer/{customerId}"], method = [RequestMethod.GET])
//    fun deleteCustomer(@PathVariable("customerId") customerId: Int) : Boolean {
//
//        return customerService.deleteCustomer(customerId)
//    }
}