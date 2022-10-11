package com.easysystems.easyorderservice

import com.easysystems.easyorderservice.data.Employee
import com.easysystems.easyorderservice.data.TabletopDTO
import mu.KLogging

object Authenticate {

    private val log : KLogging = KLogging()

    fun authenticateEmployee(employee: Employee, name: String, password: String) : Boolean
    {
        if (employee.name == name && employee.password == password)
        {
            log.logger.info("Employee $name is authenticated")
            return true
        }else{
            log.logger.info("Employee $name is not authenticated")
            return false
        }
    }

    fun authenticateTable(tabletopDTO: TabletopDTO, code: String) : Boolean
    {
            if (tabletopDTO.code == code)
            {
                log.logger.info("Table ${tabletopDTO.id} is authenticated by customer")
                return true
            } else {
                log.logger.warn("Provided code is not recognized. Authentication failed")
                return false
            }
    }
}