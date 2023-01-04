package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.TabletopDTO
import com.easysystems.easyorderservice.exceptions.TabletopNotFoundException
import com.easysystems.easyorderservice.repositories.TabletopRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class TabletopService(val tabletopRepository: TabletopRepository) {

    companion object : KLogging()

    fun createTabletop(tabletopDTO: TabletopDTO): TabletopDTO {

        val tabletop = tabletopDTO.convertToTabletop(id = null)

        tabletopRepository.save(tabletop)
        logger.info("New table created: $tabletop")

        return tabletop.convertToTabletopDTO()
    }

    fun createTabletopsByList(tabletopList: ArrayList<TabletopDTO>): ArrayList<TabletopDTO> {

        return ArrayList<TabletopDTO>().apply {
            tabletopList.forEach { tabletopDTO ->
                createTabletop(tabletopDTO).let { newTabletopDTO ->
                    this.add(newTabletopDTO)
                }
            }
        }
    }

    fun retrieveTabletopById(id: Int): TabletopDTO {

        val tabletop = tabletopRepository.findById(id)

        return if (tabletop.isPresent) {
            tabletop.get().convertToTabletopDTO()
        } else {
            throw TabletopNotFoundException("No tabletop found for given id: $id")
        }
    }

    fun retrieveAllTabletops(): ArrayList<TabletopDTO> {

        return tabletopRepository.findAll()
            .map {it.convertToTabletopDTO()
            } as ArrayList<TabletopDTO>
    }

    fun updateTabletop(id: Int, tabletopDTO: TabletopDTO): TabletopDTO {

        val tabletop = tabletopRepository.findById(id)

        return if (tabletop.isPresent) {
            tabletop.get().let {

                it.authCode = tabletopDTO.authCode
                tabletopRepository.save(it)
                it.convertToTabletopDTO()
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