package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.TabletopDTO
import com.easysystems.easyorderservice.entities.Tabletop
import com.easysystems.easyorderservice.exceptions.TabletopNotFoundException
import com.easysystems.easyorderservice.repositories.TabletopRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class TabletopService(val tabletopRepository: TabletopRepository) {

    companion object : KLogging()

    fun createTabletop(tabletopDTO: TabletopDTO): TabletopDTO {

        val tabletop = Tabletop(null, tabletopDTO.authCode)

        tabletopRepository.save(tabletop)

        logger.info("New table created: $tabletop")

        return tabletop.let {
            TabletopDTO(it.id, it.authCode)
        }
    }

    fun createTabletopsByList(tabletopList: ArrayList<TabletopDTO>): ArrayList<TabletopDTO> {

        val createdTabletops = ArrayList<TabletopDTO>()

        tabletopList.forEach { outer ->
            createTabletop(outer).let { inner ->
                createdTabletops.add(inner)
            }
        }

        return createdTabletops
    }

    fun retrieveTabletopById(id: Int): TabletopDTO {

        val tabletop = tabletopRepository.findById(id)

        return if (tabletop.isPresent) {
            tabletop.get().let {
                TabletopDTO(it.id, it.authCode)
            }
        } else {
            throw TabletopNotFoundException("No tabletop found for given id: $id")
        }
    }

    fun retrieveAllTabletops(): ArrayList<TabletopDTO> {

        return tabletopRepository.findAll()
            .map {
                TabletopDTO(it.id, it.authCode)
            } as ArrayList<TabletopDTO>
    }

    fun updateTabletop(id: Int, tabletopDTO: TabletopDTO): TabletopDTO {

        val tabletop = tabletopRepository.findById(id)

        return if (tabletop.isPresent) {
            tabletop.get().let {
                it.authCode = tabletopDTO.authCode
                tabletopRepository.save(it)

                TabletopDTO(it.id, it.authCode)
            }
        } else {
            throw TabletopNotFoundException("No tabletop found for given id: $id")
        }
    }

    fun deleteTabletop(id: Int) {

        val tabletop = tabletopRepository.findById(id)

        if (tabletop.isPresent) {
            tabletop.get().let {
                tabletopRepository.deleteById(id)
            }
        } else {
            throw TabletopNotFoundException("No tabletop found for given id: $id")
        }
    }
}