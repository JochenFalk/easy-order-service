package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.interfaces.CRUDRepository

var mainItemListDTO: ArrayList<ItemDTO> = ArrayList()

class ItemRepository : CRUDRepository {

    init {
        for (i in 1..5) {

            var category: ItemDTO.Category

            when (i) {
                1 -> {category = ItemDTO.Category.APPETIZER}
                2 -> {category = ItemDTO.Category.MAIN}
                3 -> {category = ItemDTO.Category.DESERT}
                4 -> {category = ItemDTO.Category.DRINKS}
                5 -> {category = ItemDTO.Category.DRINKS}
                else -> {
                    category = ItemDTO.Category.APPETIZER
                }
            }

            val itemDTO = ItemDTO(name = "item$i", price = 1 + i.toFloat(), category = category)
            mainItemListDTO.add(itemDTO)
        }
    }

    override fun getById(id: Int): ItemDTO? {

        for (i in mainItemListDTO) {
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
}