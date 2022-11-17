package com.easysystems.easyorderservice.data

import javax.validation.constraints.NotBlank

data class ItemDTO(
    var id: Int? = 0,
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
}