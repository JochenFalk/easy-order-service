package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.CustomerDTO
import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.data.TabletopDTO
import com.easysystems.easyorderservice.entities.Customer
import com.easysystems.easyorderservice.entities.Item
import com.easysystems.easyorderservice.entities.Tabletop
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
            TabletopDTO(it.id, it.code, it.customers as ArrayList<CustomerDTO>, it.items as ArrayList<ItemDTO>)
        }
    }

    fun retrieveTabletopById(id: Int): TabletopDTO {

        val tabletop = tabletopRepository.findById(id)

        return if (tabletop.isPresent) {
            tabletop.get().let {
                TabletopDTO(it.id, it.code, it.customers as ArrayList<CustomerDTO>, it.items as ArrayList<ItemDTO>)
            }
        } else {
            throw TabletopNotFoundException("No tabletop found for given id: $id")
        }
    }

    fun retrieveAllTabletops(): ArrayList<TabletopDTO> {

        return tabletopRepository.findAll()
            .map {
                TabletopDTO(it.id, it.code, it.customers as ArrayList<CustomerDTO>, it.items as ArrayList<ItemDTO>)
            } as ArrayList<TabletopDTO>
    }

    fun updateTabletop(id: Int, tabletopDTO: TabletopDTO): TabletopDTO {

        val tabletop = tabletopRepository.findById(id)

        return if (tabletop.isPresent) {
            tabletop.get().let {
                it.code = tabletopDTO.code
                it.customers = tabletopDTO.customersDTO as ArrayList<Customer>
                it.items = tabletopDTO.itemsDTO as ArrayList<Item>
                tabletopRepository.save(it)

                TabletopDTO(it.id, it.code, it.customers as ArrayList<CustomerDTO>, it.items as ArrayList<ItemDTO>)
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