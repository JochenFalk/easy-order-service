package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.entities.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepository: CrudRepository<Customer, Int> {

}