package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.EmployeeDTO
import com.easysystems.easyorderservice.data.TabletopDTO
import com.easysystems.easyorderservice.entities.Employee
import com.easysystems.easyorderservice.entities.Tabletop
import com.easysystems.easyorderservice.exceptions.EmployeeNotFoundException
import com.easysystems.easyorderservice.repositories.EmployeeRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class EmployeeService(val employeeRepository: EmployeeRepository) {

    companion object : KLogging()

    fun createEmployee(employeeDTO: EmployeeDTO): EmployeeDTO {

        val employee = employeeDTO.let {
            Employee(null, it.name, it.password)
        }

        employeeRepository.save(employee)

        logger.info("New employee created: $employee")

        return employee.let {
            EmployeeDTO(it.id, it.name, it.password, it.tabletops as ArrayList<TabletopDTO>)
        }
    }

    fun retrieveEmployeeById(id: Int): EmployeeDTO {

        val employee = employeeRepository.findById(id)

        return if (employee.isPresent) {
            employee.get().let {
                EmployeeDTO(it.id, it.name, it.password, it.tabletops as ArrayList<TabletopDTO>)
            }
        } else {
            throw EmployeeNotFoundException("No employee found for given id: $id")
        }
    }

    fun retrieveAllEmployees(): ArrayList<EmployeeDTO> {

        return employeeRepository.findAll()
            .map {
                EmployeeDTO(it.id, it.name, it.password, it.tabletops as ArrayList<TabletopDTO>)
            } as ArrayList<EmployeeDTO>
    }

    fun updateEmployee(id: Int, employeeDTO: EmployeeDTO): EmployeeDTO {

        val employee = employeeRepository.findById(id)

        return if (employee.isPresent) {
            employee.get().let {
                it.name = employeeDTO.name
                it.password = employeeDTO.password
                it.tabletops = employeeDTO.tabletopsDTO as ArrayList<Tabletop>
                employeeRepository.save(it)

                EmployeeDTO(it.id, it.name, it.password, it.tabletops as ArrayList<TabletopDTO>)
            }
        } else {
            throw EmployeeNotFoundException("No employee found for given id: $id")
        }
    }

    fun deleteEmployee(id: Int) {

        val employee = employeeRepository.findById(id)

        if (employee.isPresent)
        {
            employee.get().let {
                employeeRepository.deleteById(id)
            }
        } else {
            throw EmployeeNotFoundException("No employee found for given id: $id")
        }
    }
}