package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.EmployeeDTO
import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.services.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/employees")
@Validated
class EmployeeController(val employeeService: EmployeeService ) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createEmployee(@RequestBody @Valid employeeDTO: EmployeeDTO) : EmployeeDTO {

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
    fun updateEmployee(@RequestBody @Valid employeeDTO: EmployeeDTO, @PathVariable("id") id: Int): EmployeeDTO {
        return employeeService.updateEmployee(id, employeeDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteEmployee(@PathVariable("id") id: Int) {
        return employeeService.deleteEmployee(id)
    }

//    @PutMapping("/{tabletopId}/{employeeId}/{name}/{password}")
//    fun assignEmployeeToTabletop(@PathVariable tabletopId: Int,
//                                 @PathVariable employeeId: Int,
//                                 @PathVariable name: String,
//                                 @PathVariable password: String): Boolean {
//        return employeeService.assignEmployeeToTabletop(tabletopId, employeeId, name, password)
//    }
}