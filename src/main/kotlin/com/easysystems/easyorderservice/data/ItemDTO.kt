package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.entities.Session
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ItemDTO(
    var id: Int? = 0,
    @get:NotBlank(message = "Property 'name' cannot be blank")
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
}