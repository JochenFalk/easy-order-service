package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.TabletopDTO
import com.easysystems.easyorderservice.entities.Tabletop
import com.easysystems.easyorderservice.exceptions.CustomerNotFoundException
import com.easysystems.easyorderservice.exceptions.TabletopNotFoundException
import com.easysystems.easyorderservice.repositories.TabletopRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class TabletopService(val tabletopRepository: TabletopRepository) {

    companion object : KLogging()

    fun createTabletop(tabletopDTO: TabletopDTO): TabletopDTO {

        val tabletop = tabletopDTO.let {
            Tabletop(null, it.code)
        }

        tabletopRepository.save(tabletop)

        logger.info("New table created: $tabletop")

        return tabletop.let {
            TabletopDTO(it.id, it.code, it.orders)
        }
    }

    fun retrieveTabletopById(id: Int): TabletopDTO {

        val tabletop = tabletopRepository.findById(id)

        return if (tabletop.isPresent) {
            tabletop.get().let {
                TabletopDTO(it.id, it.code, it.orders)
            }
        } else {
            throw TabletopNotFoundException("No tabletop found for given id: $id")
        }
    }

    fun retrieveAllTabletops(): ArrayList<TabletopDTO> {

        return tabletopRepository.findAll()
            .map {
                TabletopDTO(it.id, it.code, it.orders)
            } as ArrayList<TabletopDTO>
    }

    fun updateTabletop(id: Int, tabletopDTO: TabletopDTO): TabletopDTO {

        val tabletop = tabletopRepository.findById(id)

        return if (tabletop.isPresent) {
            tabletop.get().let {
                it.code = tabletopDTO.code
                it.orders = tabletopDTO.orders
                tabletopRepository.save(it)

                TabletopDTO(it.id, it.code, it.orders)
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

    fun addOrderToTabletop(tabletopId: Int, customerId: Int, orderList: List<Int>): Boolean {

        val tabletop = tabletopRepository.findById(tabletopId)

        if (tabletop.isPresent) {

            tabletop.get().let {

                it.orders.addAll(orderList)
                tabletopRepository.save(it)

                logger.info("${orderList.size} order(s) have been added to table $tabletopId")
                return true
            }
        } else {
            throw TabletopNotFoundException("No table found for given id: $tabletopId")
        }
    }
}