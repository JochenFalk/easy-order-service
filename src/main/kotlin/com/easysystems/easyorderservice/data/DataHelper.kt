package com.easysystems.easyorderservice.data

class DataHelper {

    fun filterItemsByCategory(list: ArrayList<ItemDTO>, filter: ItemDTO.Category): List<ItemDTO> {

        return list
            .asSequence()
            .filter { it.category == filter }
            .toList()
    }
}