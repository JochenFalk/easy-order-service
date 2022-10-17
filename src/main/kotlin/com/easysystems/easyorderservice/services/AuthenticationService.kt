package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.EmployeeDTO
import com.easysystems.easyorderservice.data.TabletopDTO
import com.easysystems.easyorderservice.exceptions.EmployeeNotFoundException
import com.easysystems.easyorderservice.exceptions.TabletopNotFoundException
import com.easysystems.easyorderservice.repositories.EmployeeRepository
import com.easysystems.easyorderservice.repositories.TabletopRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val employeeRepository: EmployeeRepository,
    private val tabletopRepository: TabletopRepository
) {

    private val log = KLogging()

    fun employee(employeeId: Int, name: String, password: String): Boolean {

        val employee = employeeRepository.findById(employeeId)

        if (employee.isPresent) {
            employee.get().let {

                val employeeDTO = EmployeeDTO(it.id, it.name, it.password)

                return if (employeeDTO.name == name && employeeDTO.password == password) {
                    log.logger.info("Employee $name is authenticated")
                    true
                } else {
                    log.logger.info("Employee $name is not authenticated")
                    false
                }
            }
        } else {
            throw EmployeeNotFoundException("No employee found for given id: $employeeId")
        }
    }

    fun tabletop(tabletopId: Int, tabletopCode: String): Boolean {

        val tabletop = tabletopRepository.findById(tabletopId)

        if (tabletop.isPresent) {
            tabletop.get().let {

                val tabletopDTO = TabletopDTO(it.id, it.authCode)

                return if (tabletopDTO.authCode == tabletopCode) {
                    log.logger.info("Table $tabletopId is authenticated by customer")
                    true
                } else {
                    log.logger.warn("Provided code is not recognized. Authentication failed")
                    false
                }
            }
        } else {
            throw TabletopNotFoundException("No table found for given id: $tabletopId")
        }
    }
}