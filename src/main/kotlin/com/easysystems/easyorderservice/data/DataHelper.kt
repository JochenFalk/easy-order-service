package com.easysystems.easyorderservice.data

class DataHelper {

    fun filterItemsByCategory(list: ArrayList<Item>, filter: Item.Category): List<Item> {

        return list
            .asSequence()
            .filter { it.category == filter }
            .toList()
    }
}