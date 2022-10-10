package com.easysystems.easyorderservice.data

data class Item(
    var id: Int=0,
    var name: String,
    var category: Category,
    var price: Float
) {

    enum class Category {
        APPETIZER,
        MAIN,
        DESERT,
        DRINKS
    }

    init {
        id = mainItemList.size + 1
    }
}