package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.entities.Employee
import org.springframework.data.repository.CrudRepository

interface EmployeeRepository: CrudRepository<Employee, Int> {

}