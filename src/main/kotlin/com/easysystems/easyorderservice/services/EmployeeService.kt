package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.EmployeeDTO
import com.easysystems.easyorderservice.entities.Employee
import com.easysystems.easyorderservice.exceptions.EmployeeNotFoundException
import com.easysystems.easyorderservice.repositories.EmployeeRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class EmployeeService(val employeeRepository: EmployeeRepository, val authenticationService: AuthenticationService) {

    companion object : KLogging()

    fun createEmployee(employeeDTO: EmployeeDTO): EmployeeDTO {

        val employee = employeeDTO.let {
            Employee(null, it.name, it.password)
        }

        employeeRepository.save(employee)

        logger.info("New employee created: $employee")

        return employee.let {
            EmployeeDTO(it.id, it.name, it.password)
        }
    }

    fun retrieveEmployeeById(id: Int): EmployeeDTO {

        val employee = employeeRepository.findById(id)

        return if (employee.isPresent) {
            employee.get().let {
                EmployeeDTO(it.id, it.name, it.password)
            }
        } else {
            throw EmployeeNotFoundException("No employee found for given id: $id")
        }
    }

    fun retrieveAllEmployees(): ArrayList<EmployeeDTO> {

        return employeeRepository.findAll()
            .map {
                EmployeeDTO(it.id, it.name, it.password)
            } as ArrayList<EmployeeDTO>
    }

    fun updateEmployee(id: Int, employeeDTO: EmployeeDTO): EmployeeDTO {

        val employee = employeeRepository.findById(id)

        return if (employee.isPresent) {

            employee.get().let {
                it.name = employeeDTO.name
                it.password = employeeDTO.password
                employeeRepository.save(it)

                EmployeeDTO(it.id, it.name, it.password)
            }
        } else {
            throw EmployeeNotFoundException("No employee found for given id: $id")
        }
    }

    fun deleteEmployee(id: Int) {

        val employee = employeeRepository.findById(id)

        if (employee.isPresent) {

            employee.get().let {
                employeeRepository.deleteById(id)
            }
        } else {
            throw EmployeeNotFoundException("No employee found for given id: $id")
        }
    }

//    fun assignEmployeeToTabletop(tabletopId: Int, employeeId: Int, name: String, password: String): Boolean {
//
//        val employee = employeeRepository.findById(employeeId)
//
//        if (employee.isPresent) {
//
//            employee.get().let {
//
//                if (authenticationService.employee(employeeId, name, password)) {
//                    it.tabletops.add(tabletopId)
//                    employeeRepository.save(it)
//
//                    logger.info("Table $tabletopId is assigned to ${it.name}")
//                    return true
//                } else {
//                    logger.info("Table $tabletopId is not assigned! Authentication for employee ${it.name} failed")
//                    return false
//                }
//            }
//        } else {
//            throw EmployeeNotFoundException("No employee found for given id: $employeeId")
//        }
//    }
}