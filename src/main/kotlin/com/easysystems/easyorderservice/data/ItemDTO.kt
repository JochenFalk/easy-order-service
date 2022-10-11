package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.repositories.mainItemListDTO

data class ItemDTO(
    var id: Int?=0,
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
        id = mainItemListDTO.size + 1
    }
}