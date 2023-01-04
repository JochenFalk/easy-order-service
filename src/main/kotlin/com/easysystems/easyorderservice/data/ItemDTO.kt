package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.entities.Item
import javax.validation.constraints.NotBlank

data class ItemDTO(
    var id: Int?,
    @get:NotBlank(message = "Property 'name' cannot be blank")
    var name: String,
    var category: Category,
    var price: Double
) {
    enum class Category {
        APPETIZER,
        MAIN,
        DESERT,
        DRINKS
    }

    fun convertToItem(id: Int?): Item {
        return Item(
            id = id ?: this.id,
            name = this.name,
            category = this.category.toString(),
            price = this.price
        )
    }
}