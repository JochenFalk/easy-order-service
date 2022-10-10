package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.interfaces.Repository

var mainItemList: ArrayList<Item> = ArrayList()

class ItemRepository : Repository {

    init {
        for (i in 1..5) {

            var category: Item.Category

            when (i) {
                1 -> {category = Item.Category.APPETIZER}
                2 -> {category = Item.Category.MAIN}
                3 -> {category = Item.Category.DESERT}
                4 -> {category = Item.Category.DRINKS}
                5 -> {category = Item.Category.DRINKS}
                else -> {
                    category = Item.Category.APPETIZER
                }
            }

            val item = Item(name = "item$i", price = 1 + i.toFloat(), category = category)
            mainItemList.add(item)
        }
    }

    override fun getById(id: Int): Item? {

        for (i in mainItemList) {
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