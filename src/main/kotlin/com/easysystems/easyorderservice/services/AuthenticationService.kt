package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.exceptions.TabletopNotFoundException
import com.easysystems.easyorderservice.repositories.TabletopRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val tabletopRepository: TabletopRepository
) {

    private val log = KLogging()

    fun authenticateTableCode(tabletopId: Int, tabletopCode: String): Boolean {

        val tabletop = tabletopRepository.findById(tabletopId)

        if (tabletop.isPresent) {
            tabletop.get().let {

                return if (it.authCode == tabletopCode) {
                    log.logger.info("Table $tabletopId is authenticated by customer")
                    true
                } else {
                    log.logger.warn("Provided code is not recognized. Authentication failed. Code: $tabletopCode")
                    false
                }
            }
        } else {
            throw TabletopNotFoundException("No table found for given id: $tabletopId")
        }
    }
}