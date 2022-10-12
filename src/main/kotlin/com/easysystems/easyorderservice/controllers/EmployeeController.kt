package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.EmployeeDTO
import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.services.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/employees")
class EmployeeController(val employeeService: EmployeeService ) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createEmployee(@RequestBody employeeDTO: EmployeeDTO) : EmployeeDTO {

        return employeeService.createEmployee(employeeDTO)
    }

    @GetMapping("/{id}")
    fun retrieveEmployeeById(@PathVariable("id") id: Int): EmployeeDTO {
        return employeeService.retrieveEmployeeById(id)
    }

    @GetMapping
    fun retrieveAllItems() : ArrayList<EmployeeDTO> {
        return employeeService.retrieveAllEmployees()
    }

    @PutMapping("/{id}")
    fun updateEmployee(@RequestBody employeeDTO: EmployeeDTO, @PathVariable("id") id: Int): EmployeeDTO {
        return employeeService.updateEmployee(id, employeeDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteEmployee(@PathVariable("id") id: Int) {
        return employeeService.deleteEmployee(id)
    }
}