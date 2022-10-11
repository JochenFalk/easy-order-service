package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.data.TabletopDTO
import com.easysystems.easyorderservice.interfaces.CRUDRepository

var mainTabletopListDTO: ArrayList<TabletopDTO> = ArrayList()

class TabletopRepository : CRUDRepository {

    init {
        for (i in 1..5) {
            TabletopDTO(code = "code$i")
        }
    }

    override fun getById(id: Int): TabletopDTO? {

        for (i in mainTabletopListDTO) {
            if (i.id == id) {
                return i
            }
        }

        return null
    }

    override fun saveById(id: Int): Boolean {
        //
        return true
    }

    fun getTotal(tableTopDTO: TabletopDTO): Float {
        return tableTopDTO.itemsDTO
            .map { it.price }
            .sum()
    }
}